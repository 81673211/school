package com.school.biz.service.wechat.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.dao.customer.CustomerMapper;
import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.enumeration.DistributionTypeEnum;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.OrderStatusEnum;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.enumeration.SendExpressStatusEnum;
import com.school.biz.util.AmountUtils;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.service.wechat.WxPayService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ExpressReceiveService expressReceiveService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private ExpressSendService expressSendService;

    @Override
    public TreeMap<String, String> doUnifiedOrder(String orderNo) throws Exception {

        OrderInfo orderInfo = orderInfoMapper.findByOrderNo(orderNo);

        if (orderInfo == null) {
            throw new Exception("订单不存在");
        }

        if (OrderStatusEnum.SUCCESS.getCode().equals(orderInfo.getStatus())) {
            throw new Exception("订单已经支付成功，请勿重复支付");
        }
        if (OrderStatusEnum.PAYING.getCode().equals(orderInfo.getStatus())) {
            throw new Exception("订单支付处理中，请勿重复支付");
        }
        if (OrderStatusEnum.EXPIRED.getCode().equals(orderInfo.getStatus())) {
            throw new Exception("订单已过期，请重新创建订单");
        }
        if (OrderStatusEnum.FAILED.getCode().equals(orderInfo.getStatus())) {
            throw new Exception("订单已经支付失败，请重新创建订单");
        }

        TreeMap<String, String> resultMap = this.doUnifiedOrderToWxPay(orderInfo);

        return resultMap;
    }

    private TreeMap<String, String> doUnifiedOrderToWxPay(OrderInfo orderInfo) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", orderInfo.getTradeSummary());
        data.put("out_trade_no", orderInfo.getOrderNo());
        data.put("fee_type", "CNY");
        data.put("total_fee", AmountUtils.changeY2F(orderInfo.getAmount().toString()));
        data.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
        data.put("notify_url", orderInfo.getNotifyUrl());
        data.put("trade_type", "JSAPI");

        Long customerId = orderInfo.getCustomerId();
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        if (StringUtils.isBlank(customer.getPhone())) {
            throw new Exception("请先完善资料");
        }
        String openId = customer.getOpenId();

        data.put("openid", openId);

        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);

            Map<String, String> result = wxpay.unifiedOrder(data);
            log.info(JSON.toJSONString(result));
            TreeMap<String, String> treeMap = new TreeMap<String, String>();

            if ("SUCCESS".equals(result.get("result_code"))) {
                treeMap.put("appId", ConfigProperties.WXPAY_APP_ID);
                treeMap.put("nonceStr", result.get("nonce_str"));
                treeMap.put("package", "prepay_id=" + result.get("prepay_id"));
                treeMap.put("signType", WXPayConstants.HMACSHA256);
                treeMap.put("timeStamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
                String paySign = WXPayUtil.generateSignature(treeMap, config.getKey(), SignType.HMACSHA256);
                treeMap.put("paySign", paySign);
                treeMap.put("openId", openId);
                // 将订单置为支付处理中
                orderInfoService.orderPaying(orderInfo);
            } else {
                throw new Exception("微信统一下单失败:" + result.get("err_code_des"));
            }
            return treeMap;
        } catch (Exception e) {
            log.error("微信统一下单失败：" + e.getMessage());
            throw new Exception("微信统一下单失败");
        }
    }

    /**
     * 修改订单状态，获取微信回调结果
     *
     * @param notifyXml
     * @return
     * @throws Exception
     */
    @Override
    public String wxPayNotify(String notifyXml) throws Exception {
        String resXml = "";
        log.info("接收到的xml：" + notifyXml);
        log.debug("收到微信异步回调：");
        log.debug(notifyXml);
        if (StringUtils.isBlank(notifyXml)) {
            log.error("xml为空!");
            return null;
        }

        String appid = getXmlPara(notifyXml, "appid");
        String bank_type = getXmlPara(notifyXml, "bank_type");
        String cash_fee = getXmlPara(notifyXml, "cash_fee");
        String fee_type = getXmlPara(notifyXml, "fee_type");
        String is_subscribe = getXmlPara(notifyXml, "is_subscribe");
        String mch_id = getXmlPara(notifyXml, "mch_id");
        String nonce_str = getXmlPara(notifyXml, "nonce_str");
        String openid = getXmlPara(notifyXml, "openid");
        String out_trade_no = getXmlPara(notifyXml, "out_trade_no");
        String result_code = getXmlPara(notifyXml, "result_code");
        String return_code = getXmlPara(notifyXml, "return_code");
        String sign = getXmlPara(notifyXml, "sign");
        String time_end = getXmlPara(notifyXml, "time_end");
        String total_fee = getXmlPara(notifyXml, "total_fee");
        String trade_type = getXmlPara(notifyXml, "trade_type");
        String transaction_id = getXmlPara(notifyXml, "transaction_id");

        //根据返回xml计算本地签名
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("appid", appid);
        treeMap.put("bank_type", bank_type);
        treeMap.put("cash_fee", cash_fee);
        treeMap.put("fee_type", fee_type);
        treeMap.put("is_subscribe", is_subscribe);
        treeMap.put("mch_id", mch_id);
        treeMap.put("nonce_str", nonce_str);
        treeMap.put("openid", openid);
        treeMap.put("out_trade_no", out_trade_no);
        treeMap.put("result_code", result_code);
        treeMap.put("return_code", return_code);
        treeMap.put("time_end", time_end);
        treeMap.put("total_fee", total_fee);
        treeMap.put("trade_type", trade_type);
        treeMap.put("transaction_id", transaction_id);

        String localSign = WXPayUtil.generateSignature(treeMap, ConfigProperties.WXPAY_KEY,
                                                       SignType.HMACSHA256);

        log.info("本地签名是：" + localSign);
        log.debug("本地签名是：" + localSign);
        log.debug("微信支付签名是：" + sign);

        //本地计算签名与微信返回签名不同||返回结果为不成功
        if (!sign.equals(localSign) || !"SUCCESS".equals(result_code) || !"SUCCESS".equals(return_code)) {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                     + "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
            log.error("验证签名失败或返回错误:" + resXml);
        } else {
            log.info("支付成功");
            log.debug("公众号支付成功，out_trade_no(订单号)为：" + out_trade_no);
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                     + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            // 校验金额是否正确
            OrderInfo orderInfo = orderInfoMapper.findByOrderNo(out_trade_no);

            if (orderInfo == null) {
                throw new Exception("订单不存在，订单号为：" + out_trade_no);
            }

            BigDecimal amtFromWx = new BigDecimal(total_fee);
            BigDecimal amtFromSys = new BigDecimal(AmountUtils.changeY2F(orderInfo.getAmount().toString()));

            if (!amtFromWx.equals(amtFromSys)) {
                throw new Exception("订单金额不一致");
            }

            // 将订单置为成功
            orderInfoService.orderSuccess(orderInfo);
            // 更新快件状态
            Integer expressType = orderInfo.getExpressType();
            if (ExpressTypeEnum.RECEIVE.getFlag() == expressType) {
                expressReceiveService.updateReceiveExpress(orderInfo.getExpressId(),
                                                           ReceiveExpressStatusEnum.WAIT_INTO_BOX.getFlag(),
                                                           DistributionTypeEnum.DISTRIBUTION.getFlag());
            } else {
                expressSendService.updateSendExpressStatus(orderInfo.getExpressId(),
                                                           SendExpressStatusEnum.WAIT_SMQJ.getFlag());
            }
        }
        return resXml;
    }

    /**
     * 解析XML 获得名称为para的参数值
     *
     * @param xml
     * @param para
     * @return
     */
    public static String getXmlPara(String xml, String para) {
        int start = xml.indexOf("<" + para + ">");
        int end = xml.indexOf("</" + para + ">");

        if (start < 0 && end < 0) {
            return null;
        }
        return xml.substring(start + ("<" + para + ">").length(), end).replace("<![CDATA[", "").replace("]]>",
                                                                                                        "");
    }

}
