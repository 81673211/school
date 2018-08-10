package com.school.biz.service.wechat;

import java.util.TreeMap;

public interface WxPayService {

	/**
	 * 微信统一下单
	 * @throws Exception 
	 */
	TreeMap<String, String> doUnifiedOrder(String orderNo) throws Exception;

	/**
	 * 微信通知
	 */
	String wxPayNotify(String notifyXml) throws Exception;
	
}
