package com.school.biz.service.wechat.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.school.biz.domain.bo.wechat.template.Template;
import com.school.biz.service.wechat.AccessTokenService;
import com.school.biz.service.wechat.TemplateService;
import com.school.biz.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 10:25
 */
@Service
@Transactional
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private static final String TEMPLATE_SEND_URL =
            "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=${ACCESS_TOKEN}";

    private static final String VALUE = "value";

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public void send(Template template) {
        String accessToken = accessTokenService.get();
        String templateSendUrl = TEMPLATE_SEND_URL.replace("${ACCESS_TOKEN}", accessToken);
        try {
            HttpUtil.post(templateSendUrl, JSON.toJSONString(template), "UTF-8", false);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
