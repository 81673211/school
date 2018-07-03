package com.school.service.wechat.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.service.wechat.OauthService;
import com.school.util.Constants;
import com.school.util.core.utils.HttpUtil;
import com.school.util.wechat.OAuthToken;
import com.school.util.wechat.WechatUrl;
import com.school.util.wechat.ConstantWeChat;
import com.school.util.wechat.UserWechat;

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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean isOAuthed(String openId) {
        String oauthTokenStr = redisTemplate.opsForValue().get(
                Constants.CACHE_NAMESPACE_OAUTH_TOKEN.replace("${openId}", openId));
        log.info("oauthTokenStr:{}", oauthTokenStr);
        return !(oauthTokenStr == null || (JSON.parseObject(oauthTokenStr, OAuthToken.class)).isExpired());
    }

    @Override
    public UserWechat getDetail(String openId) {

        return null;
    }

    @Override
    public String getOAuthUrl(String redirectUrl) {
        try {
            return WechatUrl.USER_AUTH_URL
                    .replace("${APPID}", ConstantWeChat.APPID)
                    .replace("${REDIRECT_URL}", URLEncoder.encode(redirectUrl, "utf8"))
                    .replace("${SCOPE}", ConstantWeChat.SCOPE_SNSAPI_USERINFO)
                    .replace("${STATE}", "STATE");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public String getOAuthUrl(String redirectUrl, String state) {
        try {
            String url = WechatUrl.USER_AUTH_URL
                    .replace("${APPID}", ConstantWeChat.APPID)
                    .replace("${REDIRECT_URL}", URLEncoder.encode(redirectUrl, "utf8"))
                    .replace("${SCOPE}", ConstantWeChat.SCOPE_SNSAPI_USERINFO)
                    .replace("${STATE}", state);
            log.info("---getOAuthUrl----:{}", url);
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
        log.info("----OAuthTokenUrl----:{}", getOAuthTokenUrl);
        String response;
        try {
            response = HttpUtil.get(getOAuthTokenUrl, "utf8", false);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        log.info("----getOAuthToken----response:{}", response);
        return JSON.parseObject(response, OAuthToken.class);
    }
}
