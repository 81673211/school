package com.school.util.core.utils;

import java.io.File;
import java.security.MessageDigest;

public class MD5Util {
	public static String getMD5(String md5Str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(md5Str.getBytes("utf-8"));
			//byte[] bytes = md5.digest(md5Str.getBytes());
			StringBuffer sb = new StringBuffer();
			String temp = "";
			for (byte b : bytes) {
				temp = Integer.toHexString(b & 0XFF);
				sb.append(temp.length() == 1 ? "0" + temp : temp);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getMD5(File md5File) {
		return null;
	}

	public static void main(String[] args) {
		String a="apiName=REAL_CREDIT_PAY&apiVersion=1.0.0.0&platformID=MerchTest&merchNo=210001110100250&orderNo=2014010112345&tradeDate=20140101&amt=5&merchUrl=http://www.merchant.com/handler.jsp&merchParam=&tradeSummary=&cardName=测试&idCardNo=111111111111111111&cardBankCode=&cardType=2&cardNo=1111111111111111&cardExpire=0115&cardCvn2=15122c41d776c24deddca95b1709a88f04b";
		System.out.println("a====="+getMD5(a));
	}
}
