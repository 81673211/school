package com.school.biz.service.wechat.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.school.biz.constant.Constants;
import com.school.biz.constant.WechatUrl;
import com.school.biz.service.wechat.AccessTokenService;
import com.school.biz.util.HttpUtil;
import com.school.biz.service.wechat.MenuService;
import com.school.biz.domain.bo.wechat.Menu;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 12:46
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public void create(Menu menu) {
        String accessToken = accessTokenService.get();
        // 拼装创建菜单的url
        String url = WechatUrl.MENU_CREATE_URL.replace("${ACCESS_TOKEN}", accessToken);
        // 调用接口创建菜单
        try {
            String content = HttpUtil.post(url, JSONObject.toJSONString(menu), Constants.CHARSET_UTF8, false);
            LOGGER.info("create menu response content:{}", content);
        } catch (IOException e) {
            LOGGER.error("invoke create menu request error, {}", e.getMessage());
        }
    }

    @Override
    public Menu get() {
        String accessToken = accessTokenService.get();
        try {
            String content = HttpUtil.get(WechatUrl.MENU_GET_URL.replace("${ACCESS_TOKEN}", accessToken), Constants.CHARSET_UTF8,
                                          false);
            return JSONObject.parseObject(content, Menu.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
