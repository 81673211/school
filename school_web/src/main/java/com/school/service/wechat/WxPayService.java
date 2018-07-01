package com.school.service.wechat;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public interface WxPayService {

	/**
	 * 微信统一下单
	 * @throws Exception 
	 */
	TreeMap<String, String> doUnifiedOrder(String orderNo) throws Exception;

	/**
	 * 微信通知
	 */
	String wxPayNotify(HttpServletRequest request) throws Exception;

}
