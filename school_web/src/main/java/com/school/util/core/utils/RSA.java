
package com.school.util.core.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * rsa加解密
 * @author wangcy
 *
 */
public class RSA{
		
	/**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    
	
	/**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String publicKey)
            throws Exception {
    	byte[] data = content.getBytes();
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encode(encryptedData);
    }
	
    
    /**
     * <P>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String enString, String privateKey)
            throws Exception {
    	byte[] encryptedData = Base64.decode(enString);
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }
    
    
	public static void main(String[] args) throws Exception {
		String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAFlCk3DUsoE/dIV0wFLY4K+a63ctr4JQxqNpL9xRbQBrIRCWRsopxkF3jiOhUgHLqEeOKycUxs6m+O5vJBv+xc57gzloMYBsumfvSMLchI48rY46jgsi233Lpurv7vTdGoHH+GAtVSs+9PNgF4lkYzsDsbYLXdCBSZ3kU0pvIdQIDAQAB";
		String prvkey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIAWUKTcNSygT90hXTAUtjgr5rrdy2vglDGo2kv3FFtAGshEJZGyinGQXeOI6FSAcuoR44rJxTGzqb47m8kG/7FznuDOWgxgGy6Z+9IwtyEjjytjjqOCyLbfcum6u/u9N0agcf4YC1VKz7082AXiWRjOwOxtgtd0IFJneRTSm8h1AgMBAAECgYAWrlznZIsR9O1Pdfn/apt9xOBdlKrR1nnHdWP6ca0sH2Tes5qHezsisvK9OOATHo+6vSIy0FnX3D2hjwKeDVw3d6oR8T8eUpuj3j+no7BmmHuAaZXHnewP1/lMcqG+l1GDpGKnMfP/EuH08GmJ/CvtzGW1utB44E1eLVBntqi+YQJBAPhoy5ryuZ44QYnBOhdj30X/OuhiCNP4njTS7+cDtEW3uRqzb6K4PDq37xT99NQitjUknUyRGgKuY1jQ1b39XykCQQCEAEnZiDOMnRjriE/kB1grj/a42SYa7vDN5RQwirMMo9HG0DD0+nwGNa1/4Wz70Ra6+a8ZCuUF1WlRWifDJKRtAkAsp4H6T6nIZGp7ne2YaM6pIZFwVrcpi6a87jJL/+F3KuiugRUPVjkVgR0MU/6f58GDWhqgufDIDJs1Sw0xQwvJAkBQuhxv/OwJwsnKwcKaIiO6AIFS6vpy3k5GnSKLzUdTI5zXidtm9wmCgrFo0+Ri3KyaDLkZB6CowKt4JTrMDUzBAkEAx87bk4Cxi3WTq0RRKTF+8SX4142JDlPgSyVmGk+UJ96GRIixCNj1jhct+iOFV8bk4RN4N13h29NM9rN7ov0s0g==";
		
		String tn = "670d8aa448585ba3d42ec3c5885531ce";
				
		String enString = encryptByPublicKey(tn, pubkey);
		
		System.out.println("加密："+enString);
		
		System.out.println("解密："+decryptByPrivateKey(enString, prvkey));
	}
}
