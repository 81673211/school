package com.school.service.wechat.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.school.service.wechat.OauthService;
import com.school.util.core.utils.wechat.WechatUrl;
import com.school.util.wechat.ConstantWeChat;
import com.school.util.wechat.UserWechat;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 21:24
 */
@Service
public class OauthServiceImpl implements OauthService {

    @Override
    public UserWechat getDetail(String openId) {

        return null;
    }

    @Override
    public String getOAuthUrl(){
        try {
            return WechatUrl.USER_AUTH_URL
                    .replace("${APPID}", ConstantWeChat.APPID)
                    .replace("${REDIRECT_URL}", URLEncoder.encode(ConstantWeChat.REDIRECT_URL, "utf8"))
                    .replace("${SCOPE}", ConstantWeChat.SCOPE_SNSAPI_USERINFO);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
