package com.school.util.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DigestUtil {


	/**
	 * 生成给定数据的MD5摘要.<br>
	 * 
	 * 注意：该方法默认按ISO-8859-1编码，如果处理中文建议指定字符编码{@link #md5Hex(String, String)}
	 * 
	 * @param sData
	 *            要摘要的数据
	 * @return md5 摘要字符串, 十六进制格式表示
	 */
	public static String md5Hex(String sData) {
		return md5Hex(sData, "ISO-8859-1");
	}

	/**
	 * 生成给定数据的MD5摘要.<br>
	 * 若摘要为中文，获取的MD5摘要不一样
	 * 
	 * @param sData
	 *            要摘要的数据
	 * @param charset
	 *            编码格式，若为空则默认为ISO-8859-1
	 * 
	 * @return 摘要字符串, 十六进制格式表示
	 */
	public static String md5Hex(String sData, String charset) {
		charset = StringHelper.avoidEmpty(charset, "ISO-8859-1");
		byte[] bytes;
		try {
			bytes = sData.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return StringHelper.toString(md5Checksum(bytes));
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	static byte[] md5Checksum(byte[] data) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);		}
		md5.update(data);
		return md5.digest();
	}

}
