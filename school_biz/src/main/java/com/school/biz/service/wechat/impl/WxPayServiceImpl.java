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
import com.school.biz.constant.ConfigProperties;
import com.school.biz.dao.customer.CustomerMapper;
import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.enumeration.OrderStatusEnum;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.service.wechat.WxPayService;
import com.school.biz.util.AmountUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private ExpressSendService expressSendService;
    @Autowired
    private ExpressService expressService;

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
                treeMap.put("expressType", orderInfo.getExpressType().toString());
                // 将订单置为支付处理中
                orderInfoService.orderUpdateToPaying(orderInfo);
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
        log.debug("收到微信支付异步回调：");
        log.debug(notifyXml);
        if (StringUtils.isBlank(notifyXml)) {
            log.error("xml为空!");
            return null;
        }
        Map<String, String> signMap = WXPayUtil.xmlToMap(notifyXml);

        String sign = signMap.get("sign");

        log.info("微信支付签名是：" + sign);

        //本地计算签名与微信返回签名不同||返回结果为不成功
        if (!WXPayUtil.isSignatureValid(signMap, ConfigProperties.WXPAY_KEY, SignType.HMACSHA256) ||
            !"SUCCESS".equals(signMap.get("result_code")) || !"SUCCESS".equals(signMap.get("return_code"))) {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                     + "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
            log.error("验证签名失败或返回错误:" + resXml);
        } else {
            log.info("支付成功");
            String outTradeNo = signMap.get("out_trade_no");
            String totalFee = signMap.get("total_fee");
            log.debug("公众号支付成功，out_trade_no(订单号)为：" + outTradeNo);
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                     + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            // 校验金额是否正确
            OrderInfo orderInfo = orderInfoMapper.findByOrderNo(outTradeNo);

            if (orderInfo == null) {
                throw new Exception("订单不存在，订单号为：" + outTradeNo);
            }

            BigDecimal amtFromWx = new BigDecimal(totalFee);
            BigDecimal amtFromSys = new BigDecimal(AmountUtils.changeY2F(orderInfo.getAmount().toString()));

            if (!amtFromWx.equals(amtFromSys)) {
                throw new Exception("订单金额不一致");
            }

            // 将订单置为成功
            orderInfoService.orderUpdateToSuccess(orderInfo);
            // 更新快件状态
            expressService.updateExpressByPay(orderInfo);
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
