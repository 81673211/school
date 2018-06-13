package com.school.util.core.utils.wechat;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 20:01
 */
public final class WechatUrl {

    private WechatUrl() {}

    public static final String ACCESS_TOKEN_GET_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}";
    public static final String USER_AUTH_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${APPID}&redirect_uri=${REDIRECT_URL}&response_type=code&scope=${SCOPE}&state=1#wechat_redirect";
}
