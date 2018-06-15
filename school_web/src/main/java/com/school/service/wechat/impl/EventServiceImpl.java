package com.school.service.wechat.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.school.service.customer.CustomerService;
import com.school.service.wechat.OauthService;
import com.school.service.wechat.EventService;
import com.school.util.wechat.WechatEventTypeEnum;
import com.school.util.wechat.WechatMessageUtil;
import com.school.util.wechat.WechatMsgTypeEnum;
import com.school.util.wechat.message.TextMessage;
import com.school.web.wechat.WechatController;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 16:29
 */
@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);


    @Autowired
    private CustomerService customerService;
    @Autowired
    private OauthService oauthService;

    @Override
    public String process(HttpServletRequest request) throws Exception {
        Map<String, String> paramMap = WechatMessageUtil.parseXml(request);
        LOGGER.info("paramMap:{}", paramMap.toString());
        String msgType = paramMap.get("MsgType");
        LOGGER.info("msgType:{}", msgType);
        String event = paramMap.get("Event");
        LOGGER.info("event:{}", event);
        String openId = paramMap.get("FromUserName");
        if (WechatMsgTypeEnum.EVENT.getCode().equals(msgType)) {
            if (WechatEventTypeEnum.SUBSCRIBE.getCode().equals(event)) {
                customerService.subscribe(openId);
                TextMessage textMessage = new TextMessage();
                textMessage.setMsgType(WechatMsgTypeEnum.TEXT.getCode());
                textMessage.setToUserName(openId);
                textMessage.setFromUserName(paramMap.get("ToUserName"));
                textMessage.setCreateTime(System.currentTimeMillis());
                textMessage.setContent("欢迎您");
                return WechatMessageUtil.textMessageToXml(textMessage);
            } else if (WechatEventTypeEnum.VIEW.getCode().equals(event)) {

                return "success";
            }
        } else {
            return "success";
        }
        return "success";
    }
}
