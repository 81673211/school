package com.school.web.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 未决流水查询（用于更新订单状态）：处于支付处理中的订单，下单10分钟后。（每5分钟执行一次）
 */
@Slf4j
@Service(value = "NotPayOrderQuartz")
public class NotPayOrderQuartz {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private ExpressService expressService;

    public void execute() throws Exception {
        log.info("==========NotPayOrderQuartz：未决流水查询==========");
        List<OrderInfo> notPayOrderList = orderInfoService.getNotPayOrder();

        if (!notPayOrderList.isEmpty()) {
            log.info("未决流水查询:" + DateUtil.now() + "共有" + notPayOrderList.size() + "条未决流水。");
            int dealNum = 0;

            // 向上游发起订单查询
            for (OrderInfo orderInfo : notPayOrderList) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("out_trade_no", orderInfo.getOrderNo());
                data.put("nonce_str", WXPayUtil.generateUUID());

                try {
                    WXPayConfigImpl config = WXPayConfigImpl.getInstance();
                    WXPay wxpay = new WXPay(config);

                    Map<String, String> result = wxpay.orderQuery(data);
                    log.info("result:" + JSON.toJSONString(result));
                    String localSign = WXPayUtil.generateSignature(result, ConfigProperties.WXPAY_KEY,
                                                                   SignType.HMACSHA256);
                    if (!localSign.equals(result.get("sign"))) {
                        log.error("NotPayOrderQuartz验签失败");
                        continue;
                    }
                    if ("SUCCESS".equals(result.get("result_code"))) {
                        if ("SUCCESS".equals(result.get("trade_state"))) {
                            // 如果订单成功，则改为成功
                            log.info("订单查询====》订单号：" + orderInfo.getOrderNo() + "查询成功，状态为：" + result
                                    .get("trade_state"));
                            // 将订单置为成功
                            orderInfoService.orderUpdateToSuccess(orderInfo);
                            // 更新快件状态
                            expressService.updateExpressByPay(orderInfo);
                            dealNum++;
                        } else if ("CLOSED".equals(result.get("trade_state")) || "PAYERROR".equals(
                                result.get("trade_state"))) {
                            // 如果订单已经失败，则将订单置为失败
                            log.info("订单查询====》订单号：" + orderInfo.getOrderNo() + "查询成功，状态为：" + result
                                    .get("trade_state"));
                            // 将订单置为失败
                            orderInfoService.orderUpdateToFailed(orderInfo);
                            dealNum++;
                        }
                    }
                } catch (Exception e) {
                    log.error("微信订单查询失败：" + e.getMessage());
                    continue;
                }
            }

            log.info("未决流水处理结果:" + "共处理" + dealNum + "条未决流水。");
        }
    }

}
