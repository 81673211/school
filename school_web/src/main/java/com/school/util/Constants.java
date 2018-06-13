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

    public static final String CACHE_NAMESPACE_ACCESS_TOKEN = "redis:access_token";

    public static final String CACHE_NAMESPACE_OAUTH_TOKEN = "redis:oauth_token:${openId}";


    public static final String USER_AUTH_CALLBACK_URL = "http://www.glove1573.cn/getOpenId";
}
