package com.school.biz.service.wechat.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressArrivalTemplateData;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressDistributionSelfTemplateData;
import com.school.biz.domain.bo.wechat.template.Template;
import com.school.biz.domain.bo.wechat.template.TemplateData;
import com.school.biz.domain.bo.wechat.template.TemplateDataItem;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.enumeration.WechatTemplateEnum;
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

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public void send(Template template) {
        String accessToken = accessTokenService.get();
        String templateSendUrl = TEMPLATE_SEND_URL.replace("${ACCESS_TOKEN}", accessToken);
        try {
            log.info(JSON.toJSONString(template));
            HttpUtil.post(templateSendUrl, JSON.toJSONString(template), "UTF-8", false);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(String templateType, String openId, Express express, int expressType) {
        String templateId = ConfigProperties.p.getValue(templateType);
        log.info("templateId:{}", templateId);
        TemplateData templateData;
        Template template;
        if (WechatTemplateEnum.RECEIVE_EXPRESS_ARRIVAL.getType().equals(templateType)) {
            templateData = new ReceiveExpressArrivalTemplateData.Builder()
                    .buildKeyword2(express.getCompanyName())
                    .buildRemark("快递单号：${code} \r请到待收快件页面选择自提或配送，如有疑问请致电：66776677"
                                         .replace("${code}", express.getCode())).build();
            template = new Template.Builder()
                    .buildId(templateId)
                    .buildToUser(openId)
                    .buildTemplateData(templateData).build();
            send(template);
        } else if (WechatTemplateEnum.RECEIVE_EXPRESS_DISTRIBUTION_SELF.getType().equals(templateType)) {
            templateData = new ReceiveExpressDistributionSelfTemplateData.Builder()
                    .buildKeyword1(express.getCode())
                    .buildKeyword2(express.getCompanyName()).build();
            template = new Template.Builder()
                    .buildId(templateId)
                    .buildToUser(openId)
                    .buildTemplateData(templateData).build();
            send(template);
        }
    }
}
