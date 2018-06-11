package com.school.util;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 15:48
 */
public final class Constants {

    private Constants() {}

    public static final String ACCESS_TOKEN_KEY = "redis:access_token:key";

    public static final String ACCESS_TOKEN_GET_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}";
}
