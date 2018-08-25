package com.school.biz.service.wechat;

import java.util.TreeMap;

public interface WxPayService {

	/**
	 * 微信统一下单
	 * @throws Exception 
	 */
	TreeMap<String, String> doUnifiedOrder(String orderNo) throws Exception;

	/**
	 * 微信支付通知
	 */
	String wxPayNotify(String notifyXml) throws Exception;
	
	/**
	 * 微信退款通知
	 */
	String wxRefundNotify(String notifyXml) throws Exception;
	
}
