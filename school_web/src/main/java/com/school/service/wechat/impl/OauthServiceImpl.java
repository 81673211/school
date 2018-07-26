package com.school.service.wechat.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.school.service.wechat.OauthService;
import com.school.util.core.utils.HttpUtil;
import com.school.util.wechat.ConstantWeChat;
import com.school.util.wechat.OAuthToken;
import com.school.util.wechat.UserWechat;
import com.school.util.wechat.WechatUrl;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 21:24
 */
@Service
@Slf4j
public class OauthServiceImpl implements OauthService {

    @Override
    public UserWechat getDetail(String openId, String accessToken) {
        String userInfoUrl = WechatUrl.USER_INFO_URL.replace("${ACCESS_TOKEN}", accessToken).replace(
                "${OPEN_ID}", openId);
        String response;
        try {
            response = HttpUtil.get(userInfoUrl, "utf8", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject json = JSON.parseObject(response);
        return new UserWechat(json.getString("openid"),
                              json.getString("nickname"),
                              json.getIntValue("sex"),
                              json.getString("headimgurl"));
    }

    @Override
    public String getOAuthUrl(String state) {
        try {
            String url = WechatUrl.USER_AUTH_URL
                    .replace("${APPID}", ConstantWeChat.APPID)
                    .replace("${REDIRECT_URL}", URLEncoder.encode("http://www.glove1573.cn/wx/proxy", "utf8"))
                    .replace("${SCOPE}", ConstantWeChat.SCOPE_SNSAPI_USERINFO)
                    .replace("${STATE}", state);
            return url;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public OAuthToken getOAuthToken(String code) {

        String getOAuthTokenUrl = WechatUrl.OAUTH_TOKEN_GET_URL
                .replace("${APPID}", ConstantWeChat.APPID)
                .replace("${APPSECRET}", ConstantWeChat.APPSECRET)
                .replace("${CODE}", code);
        String response;
        try {
            response = HttpUtil.get(getOAuthTokenUrl, "utf8", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject json = JSON.parseObject(response);
        return new OAuthToken(json.getString("access_token"),
                              json.getIntValue("expires_in"),
                              json.getString("refresh_token"),
                              json.getString("openid"),
                              json.getString("scope"));
    }
}
