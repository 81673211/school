package com.school.biz.service.wechat.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
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
            HttpUtil.post(templateSendUrl, getContent(template), "UTF-8", false);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getContent(Template template) {
        JSONObject content = new JSONObject();
        content.put("touser", template.getToUser());
        content.put("template_id", template.getId());
        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put(VALUE, template.getFirst());
        data.put("first", first);
        List<String> keywords = template.getKeywords();
        if (!CollectionUtils.isEmpty(keywords)) {
            int index = 1;
            for (String keyword : keywords) {
                JSONObject value = new JSONObject();
                value.put(VALUE, keyword);
                data.put("keyword" + index, value);
                index++;
            }
        }
        JSONObject remark = new JSONObject();
        remark.put(VALUE, template.getRemark());
        data.put("remark", remark);
        content.put("data", data);
        log.info("template:{}", content);
        return content.toString();
    }
}
