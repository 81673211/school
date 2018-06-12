package com.school.service.base.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.school.service.base.AccessTokenService;
import com.school.util.Constants;
import com.school.util.core.utils.HttpUtil;
import com.school.util.core.utils.wechat.WechatUrl;
import com.school.util.wechat.ConstantWeChat;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 15:00
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public synchronized String get() {
        String accessToken = redisTemplate.opsForValue().get(Constants.ACCESS_TOKEN_KEY);
        if (StringUtils.isBlank(accessToken)) {
            String url = WechatUrl.ACCESS_TOKEN_GET_URL
                    .replace("${APPID}", ConstantWeChat.APPID)
                    .replace("${APPSECRET}", ConstantWeChat.APPSECRET);
            try {
                String response = HttpUtil.get(url, "utf8", false);
                if (StringUtils.isBlank(response)) {
                    throw new RuntimeException("get access_token failed, response is blank");
                }
                JSONObject jsonObject = JSON.parseObject(response);
                accessToken = jsonObject.getString("access_token");
                long timeout = jsonObject.getLongValue("expires_in");
                if (StringUtils.isBlank(accessToken) || timeout <= 0) {
                    throw new RuntimeException(
                            "get access_token failed, access_token is blank or timeout less than zero");
                }
                redisTemplate.opsForValue().set(Constants.ACCESS_TOKEN_KEY, accessToken, timeout,
                                                TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return accessToken;
    }
}
