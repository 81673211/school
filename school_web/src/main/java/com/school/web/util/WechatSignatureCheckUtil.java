package com.school.web.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

import com.school.biz.constant.ConfigProperties;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 21:38
 */
public class WechatSignatureCheckUtil {

    private WechatSignatureCheckUtil() {}

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { ConfigProperties.TOKEN, timestamp, nonce };
        //排序
        Arrays.sort(arr);

        //生成字符串
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }

        //sha1加密
        String temp = getSha1(content.toString());

        return Objects.equals(temp, signature);
    }

    private static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'
        };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
