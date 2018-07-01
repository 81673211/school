package com.school.service.wechat;

import java.util.TreeMap;

public interface WxPayService {

	/**
	 * 微信统一下单
	 */
	TreeMap<String, String> doUnifiedOrder(String openId);

}
