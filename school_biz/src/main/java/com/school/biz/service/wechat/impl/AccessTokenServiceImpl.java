package com.school.biz.service.wechat.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.constant.Constants;
import com.school.biz.util.HttpUtil;
import com.school.biz.constant.RedisKeyNS;
import com.school.biz.service.wechat.AccessTokenService;
import com.school.biz.constant.WechatUrl;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 15:00
 */
@Service
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public synchronized String get() {
        String accessToken = redisTemplate.opsForValue().get(RedisKeyNS.CACHE_BASE_ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            String url = WechatUrl.ACCESS_TOKEN_GET_URL
                    .replace("${APPID}", ConfigProperties.APPID)
                    .replace("${APPSECRET}", ConfigProperties.APPSECRET);
            try {
                String response = HttpUtil.get(url, Constants.CHARSET_UTF8, false);
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
                redisTemplate.opsForValue().set(RedisKeyNS.CACHE_BASE_ACCESS_TOKEN, accessToken, timeout,
                                                TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return accessToken;
    }
}
