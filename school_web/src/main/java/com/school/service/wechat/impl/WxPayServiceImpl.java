package com.school.service.wechat.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfigImpl;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;
import com.school.service.wechat.WxPayService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {
	@Override
	public TreeMap<String, String> doUnifiedOrder(String openId) {
		HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", DateUtil.format(new Date(), DateUtil.FILE_DATETIME_PATTERN));
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://test.letiantian.me/wxpay/notify");
        data.put("trade_type", "JSAPI");
        data.put("openid", "oSAxK1JJDAm5rT9m4K7CWBOyDb2E");

        try {
        	WXPayConfigImpl config = WXPayConfigImpl.getInstance();
        	WXPay wxpay = new WXPay(config);
        	
            Map<String, String> r = wxpay.unifiedOrder(data);
            System.out.println(r);
            System.out.println(r.get("prepay_id"));
            
            TreeMap<String, String> treeMap = new TreeMap<String,String>();
            if("SUCCESS".equals(r.get("result_code"))){
        		treeMap.put("appId", "wxedd1d1443b14145c");
        		treeMap.put("nonceStr", r.get("nonce_str"));
        		treeMap.put("package", "prepay_id=" + r.get("prepay_id"));
        		treeMap.put("signType", "MD5");
        		treeMap.put("timeStamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
        		String paySign = WXPayUtil.generateSignature(treeMap, config.getKey(),SignType.HMACSHA256);
        		System.out.println("最终签名为：" + paySign);
        		treeMap.put("paySign", paySign);
            }else{
            	throw new FuBusinessException("微信统一下单失败:" + r.get("result_msg"));
            }
            return treeMap;
        } catch (Exception e) {
        	log.error("微信统一下单失败：" + e.getMessage());
            throw new FuBusinessException("微信统一下单失败");
        }
	}
}
