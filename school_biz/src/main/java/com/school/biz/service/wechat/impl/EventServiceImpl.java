package com.school.biz.service.wechat.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.wechat.EventService;
import com.school.biz.enumeration.WechatEventTypeEnum;
import com.school.biz.util.wechat.WechatMessageUtil;
import com.school.biz.enumeration.WechatMsgTypeEnum;
import com.school.biz.domain.bo.wechat.TextMessage;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 16:29
 */
@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private static final String SUCCESS = "success";

    @Autowired
    private CustomerService customerService;

    @Override
    public String process(Map<String, String> paramMap) {
        String msgType = paramMap.get("MsgType");
        String event = paramMap.get("Event");
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
            } else if (WechatEventTypeEnum.UNSUBSCRIBE.getCode().equals(event)) {
                customerService.unsubscribe(openId);
                return SUCCESS;
            } else if (WechatEventTypeEnum.VIEW.getCode().equals(event)) {
                return SUCCESS;
            }
        } else {
            return SUCCESS;
        }
        return SUCCESS;
    }
}
