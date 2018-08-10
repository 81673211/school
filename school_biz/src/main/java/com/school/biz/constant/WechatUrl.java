package com.school.biz.constant;

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
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${APPID}&redirect_uri=${REDIRECT_URL}&response_type=code&scope=${SCOPE}&state=${STATE}#wechat_redirect";
    public static final String MENU_CREATE_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=${ACCESS_TOKEN}";
    public static final String MENU_GET_URL =
            "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=${ACCESS_TOKEN}";
    public static final String OAUTH_TOKEN_GET_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=${APPID}&secret=${APPSECRET}&code=${CODE}&grant_type=authorization_code";
    public static final String USER_INFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=${ACCESS_TOKEN}&openid=${OPEN_ID}&lang=zh_CN";
    public static final String OAUTH_TOKEN_REFRESH_URL =
            "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=${APPID}&grant_type=refresh_token&refresh_token=${REFRESH_TOKEN}";

}
