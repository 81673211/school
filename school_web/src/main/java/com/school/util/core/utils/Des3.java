/**
 * Title: MOBAO <BR>
 * Description: todo Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.util.core.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * ClassName: Des3 
 * @Description: 对称加密算法
 * @author linqingsong
 * @date Mar 26, 2015
 */
public class Des3  {  
    // 密钥  24位
	private final static String secretKey = "123456789012345678901234"; 
    // 向量  
    private final static String iv = "01234567";  
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8";  
  
    /** 
     * 3DES加密 
     *  
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */  
    public static String encode(String plainText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
        return Base64.encode(encryptData);  
    }  
  
    /** 
     * 3DES解密 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */  
    public static String decode(String encryptText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
  
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));  
  
        return new String(decryptData, encoding);  
    }  
    
    public static void main(String[]args){
    	String _str="{\"respCode\":0,\"respMsg\":\"操作成功\",\"sendId\":\"1000\"}";
    	try {
			String scresult=Des3.encode(_str);
			System.out.println("----------加密后:"+scresult);
			
			String result=Des3.decode(scresult);
			System.out.println("----------解密:"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
}  