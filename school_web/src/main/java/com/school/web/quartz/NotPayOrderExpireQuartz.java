package com.school.web.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.util.DateUtil;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.service.order.OrderInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 未决流水超时：将下单超过2小时依旧处于未支付的订单置为超时（每个小时执行一次）
 */
@Slf4j
@Service(value = "NotPayOrderExpireQuartz")
public class NotPayOrderExpireQuartz {

    @Autowired
    private OrderInfoService orderInfoService;

    public void execute() {
        log.info("==========NotPayOrderExpireQuartz：未决流水超时==========");
        List<OrderInfo> notPayOrderList = orderInfoService.getNotPayOrder();

        if (!notPayOrderList.isEmpty()) {
            log.info("未决流水超时:" + DateUtil.now() + "共有" + notPayOrderList.size() + "条未决流水超时。");
            int dealNum = 0;

            // 向上游发起关闭订单
            for (OrderInfo orderInfo : notPayOrderList) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("out_trade_no", orderInfo.getOrderNo());
                data.put("nonce_str", WXPayUtil.generateUUID());

                try {
                    WXPayConfigImpl config = WXPayConfigImpl.getInstance();
                    WXPay wxpay = new WXPay(config);

                    Map<String, String> result = wxpay.closeOrder(data);
                    log.info("result:" + JSON.toJSONString(result));
                    String localSign = WXPayUtil.generateSignature(result, ConfigProperties.WXPAY_KEY,
                                                                   SignType.HMACSHA256);
                    if (!localSign.equals(result.get("sign"))) {
                        log.error("NotPayOrderExpireQuartz验签失败");
                        continue;
                    }
                    if ("SUCCESS".equals(result.get("result_code"))) {
                        log.info("订单关闭====》订单号：" + orderInfo.getOrderNo() + "关闭成功");
                        dealNum++;
                    }
                } catch (Exception e) {
                    log.error("微信订单关闭失败：" + e.getMessage());
                    continue;
                }
            }

            log.info("未决流水超时处理结果:" + "共处理" + dealNum + "条未决流水。");
        }
    }

}
