/**
 * Title: MOBAO <BR>
 * Description: todo 
 * Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.util.core.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * ClassName: AESCodec
 * @Description:  AES加密
 * @author linqingsong
 * @date Apr 10, 2015 9:37:01 AM
 */
public class AESCodec {
	private static final String KEY_ALGORITHM = "AES";

	// 加密、解密算法/工作模式/填充方式
	private static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";

	private static final int KEY_SIZE = 128;

	private static final String CHARSET = "UTF-8";

	// 解密
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return trim(cipher.doFinal(data));
	}

	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, HexCodec.hexDecode(key));
	}

	public static String decrypt(String data, String key) throws Exception {
		return new String(decrypt(HexCodec.hexDecode(data), key), CHARSET);
	}

	// 加密
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(padding(data));
	}

	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, HexCodec.hexDecode(key));
	}

	public static String encrypt(String data, String key) throws Exception {
		return HexCodec.hexEncode(encrypt(data.getBytes(CHARSET), key));
	}

	/**
	 * 采用NoPadding模式时，若加密字符串长度不是16的倍数，则须在其后补足0x00
	 * 
	 * @param data
	 * @return
	 */
	private static byte[] padding(byte[] data) {
		return padding(data, 16);
	}
	
	private static byte[] padding(byte[] data, int len) {
		int length = data.length;
		int remainder = length % len;

		if (remainder == 0) {
			return data;
		} else {
			byte[] newData = new byte[length + (len - remainder)];
			System.arraycopy(data, 0, newData, 0, length);
			return newData;
		}
	}

	private static byte[] trim(byte[] data) {
		int length = data.length;

		int counter = 0;
		for (int i = 1; i < 17; i++) {
			if (data[length - i] == (byte) 0x00) {
				counter++;
			}
		}

		return Arrays.copyOfRange(data, 0, (length - counter));
	}

	// 生成一个AES密钥
	public static byte[] genKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(KEY_SIZE);
		SecretKey secretKey = kg.generateKey();
		byte[] key = secretKey.getEncoded();
		return key;
	}

	private static byte[] encryptMD5(String data) {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException gse) {
			//ignore, must not be here
		} catch (UnsupportedEncodingException uee) {
			//ignore, must not be here
		}
		return bytes;
	}
	
	/**
	 * 产生一个用于AES加密的KEY
	 * @param factor
	 * @param salt
	 * @return
	 */
	public static String generateAESKey(String factor, String salt) {
		String key = HexCodec.hexEncode(encryptMD5(factor));
		key = HexCodec.hexEncode(encryptMD5(key + salt));
		return key;
	}
	
	public static void main(String[] args) throws Exception {
		String charset = "UTF-8";
		String raw = "123";
		byte[] data = raw.getBytes(charset);
		System.out.println("明文：" + raw + ", 长度=" + data.length);
		// 产生一个密钥
		// byte[] key = genKey();
		String strKey = generateAESKey("210003510003000", "merchtest");
		//System.out.println(strKey);
		byte[] key = HexCodec.hexDecode(strKey);
		System.out.println("密钥：" + HexCodec.hexEncode(key) + ", 长度=" + key.length);
		// 加密
		byte[] secret = encrypt(data, key);
		System.out.println("密文：" + HexCodec.hexEncode(secret) + ", 长度=" + secret.length);
		// 解密
		byte[] origin = decrypt(secret, key);
		System.out.println("解密后：" + new String(origin, charset) + ", 长度=" + origin.length);
		System.out.println();
		//String aesKey = generateAESKey("210003510003000", "merchtest");
		String aesKey = "DB1CA2EA84352B599658A5D8939A3F7F";
		System.out.println(aesKey);
		//System.out.println("解密:" + decrypt("765EF5A8B3C6A2A87CBB0F4F8A9FC9E2", aesKey));
		String str = encrypt("123", aesKey);
		System.out.println("加密：" + str);
		System.out.println("解密:" + decrypt(str, aesKey));
		//System.out.println(encrypt("789", aesKey));
		//System.out.println(generateAESKey("210004510003452","txqx123"));
		
		System.out.println(java.net.URLEncoder.encode("e80HrvNcmtq2rAAQfggul+XSfci/HGu9txOALbE9Y6+8irdBkWD2LbjfNKmT4w8PYZnwU4C5YZsY5V1PFVFwwwu327CJE1FU77PUvbSu3QVUINTRcL9z7Q5UwRKnltOgqINx9i0UY5Jy874939kYbuQH1JFEyWQD22qOGHlgTJM="));
	}
}
