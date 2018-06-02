package com.school.util.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


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
	 * 注意：该方法默认按UTF-8编码
	 * 
	 * @param sData
	 *            要摘要的数据
	 * @return md5摘要字符串, 十六进制格式表示
	 */
	public static String md5HexFromUTF8(String sData) {
		return md5Hex(sData, "UTF-8");
	}

	/**
	 * 生成给定数据的MD5摘要.<br>
	 * 若摘要为中文，获取的MD5摘要不一样,具体参见单元测试{@link DigestUtilTest}
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
	 * 生成给定文件的MD5摘要.
	 * 
	 * @param file
	 * @return
	 */
	public static String md5Hex(File file) {
		InputStream is;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);		
			}
		return StringHelper.toString(md5Checksum(is));
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

	/**
	 * 
	 * @param is
	 * @return
	 */
	static byte[] md5Checksum(InputStream is) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);		
			}

		DigestInputStream dis = null;
		try {
			dis = new DigestInputStream(is, md5);

			byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
			int read = dis.read(buffer, 0, STREAM_BUFFER_LENGTH);

			while (read > -1) {
				read = dis.read(buffer, 0, STREAM_BUFFER_LENGTH);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);		
		} finally {
			CloseUtil.closeInputStream(dis);
		}
		return md5.digest();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void listSecuProvidersAndServiceTypes() {
		Provider[] providers = Security.getProviders();
		Set result = new HashSet();
		for (int i = 0; i < providers.length; i++) {
			// Get services provided by each provider
			Set keys = providers[i].keySet();
			for (Iterator it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				key = key.split(" ")[0];
    
                if (key.startsWith("Alg.Alias.")) {
					// Strip the alias
					key = key.substring(10);
				}
				int ix = key.indexOf('.');
				result.add(key.substring(0, ix));
			}
		}
    }

	private static final int STREAM_BUFFER_LENGTH = 1024;


}
