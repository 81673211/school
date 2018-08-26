package com.school.manager.quartz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.RefundOrderInfoService;
import com.school.biz.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 未决退款查询
 */
@Slf4j
@Service(value = "NotRefundOrderQuartz")
public class NotRefundOrderQuartz {

	@Autowired
	private RefundOrderInfoService refundOrderInfoService;
	@Autowired
	private ExpressService expressService;
	
    public void execute() throws Exception {
        log.info("==========NotRefundOrderQuartz：未决退款查询==========");
        List<RefundOrderInfo> notRefundOrderList = refundOrderInfoService.getNotRefundOrder();

        if (!notRefundOrderList.isEmpty()) {
            log.info("未决退款查询:" + DateUtil.now() + "共有" + notRefundOrderList.size() + "条未决退款。");
            int dealNum = 0;

            // 向上游发起退款查询
            for (RefundOrderInfo refundOrderInfo : notRefundOrderList) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("out_refund_no", refundOrderInfo.getRefundOrderNo());
                data.put("nonce_str", WXPayUtil.generateUUID());

                try {
                    WXPayConfigImpl config = WXPayConfigImpl.getInstance();
                    WXPay wxpay = new WXPay(config);

                    Map<String, String> result = wxpay.refundQuery(data);
                    log.info("result:" + JSON.toJSONString(result));
                    String localSign = WXPayUtil.generateSignature(result, ConfigProperties.WXPAY_KEY,
                                                                   SignType.HMACSHA256);
                    if (!localSign.equals(result.get("sign"))) {
                        log.error("NotRefundOrderQuartz验签失败");
                        continue;
                    }
                    if ("SUCCESS".equals(result.get("result_code"))) {
                        if ("SUCCESS".equals(getMapLike("refund_status", result))) {
                            // 如果退款成功，则改为成功
                            log.info("退款查询====》退款订单号：" + refundOrderInfo.getRefundOrderNo() + "查询成功，状态为：" + getMapLike("refund_status", result));
                            // 将订单置为成功
                            refundOrderInfoService.refundOrderUpdateToSuccess(refundOrderInfo);
                            dealNum++;
                        } else if ("REFUNDCLOSE".equals(getMapLike("refund_status", result))) {
                            // 如果退款已经失败，则将退款订单置为失败
                            log.info("退款查询====》退款订单号：" + refundOrderInfo.getRefundOrderNo() + "查询成功，状态为：" + getMapLike("refund_status", result));
                            // 将订单置为失败
                            refundOrderInfoService.refundOrderUpdateToFailed(refundOrderInfo);
                            dealNum++;
                        }
                    }
                } catch (Exception e) {
                    log.error("微信退款查询失败：" + e.getMessage());
                    continue;
                }
            }

            log.info("未决退款处理结果:" + "共处理" + dealNum + "条未决退款。");
        }
    }
    public static String getMapLike(String key, Map<String, String> map) {
    	String value = null;
    	Iterator it = map.entrySet().iterator();
    	while(it.hasNext()) {
    		Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
    		if (entry.getKey().indexOf(key) != -1) {
    			value = entry.getValue();
    		}
    	}
    	return value;
    }
    
    public static void main(String[] args) {
		String refundOrderNo = "171032292284969779200";
		
		HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_refund_no", refundOrderNo);
        data.put("nonce_str", WXPayUtil.generateUUID());

        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);

            Map<String, String> result = wxpay.refundQuery(data);
            log.info("result:" + JSON.toJSONString(result));
            String localSign = WXPayUtil.generateSignature(result, ConfigProperties.WXPAY_KEY,
                                                           SignType.HMACSHA256);
            if (!localSign.equals(result.get("sign"))) {
                log.error("NotRefundOrderQuartz验签失败");
            }
            if ("SUCCESS".equals(result.get("result_code"))) {
                if ("SUCCESS".equals(getMapLike("refund_status", result))) {
                    // 如果退款成功，则改为成功
                    log.info("退款查询====》退款订单号：" + refundOrderNo + "查询成功，状态为：" + getMapLike("refund_status", result));
                    // 将订单置为成功
                } else if ("REFUNDCLOSE".equals(getMapLike("refund_status", result))) {
                    // 如果退款已经失败，则将退款订单置为失败
                    log.info("退款查询====》退款订单号：" + refundOrderNo + "查询成功，状态为：" + getMapLike("refund_status", result));
                    // 将订单置为失败
                }
            }
        } catch (Exception e) {
            log.error("微信退款查询失败：" + e.getMessage());
        }
	}
    
}
