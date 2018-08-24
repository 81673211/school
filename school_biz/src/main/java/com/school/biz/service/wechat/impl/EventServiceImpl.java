package com.school.biz.service.wechat.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.biz.domain.bo.wechat.message.Item;
import com.school.biz.domain.bo.wechat.message.NewsMessage;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.wechat.EventService;
import com.school.biz.enumeration.WechatEventTypeEnum;
import com.school.biz.util.wechat.WechatMessageUtil;
import com.school.biz.enumeration.WechatMsgTypeEnum;
import com.school.biz.domain.bo.wechat.message.TextMessage;

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
//                TextMessage textMessage = new TextMessage();
//                textMessage.setMsgType(WechatMsgTypeEnum.TEXT.getCode());
//                textMessage.setToUserName(openId);
//                textMessage.setFromUserName(paramMap.get("ToUserName"));
//                textMessage.setCreateTime(System.currentTimeMillis());
//                textMessage.setContent("欢迎您");
//                return WechatMessageUtil.textMessageToXml(textMessage);
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setMsgType(WechatMsgTypeEnum.NEWS.getCode());
                newsMessage.setToUserName(openId);
                newsMessage.setFromUserName(paramMap.get("ToUserName"));
                newsMessage.setCreateTime(System.currentTimeMillis());
                newsMessage.setArticleCount(1);
                List<Item> items = new ArrayList<>();
                Item item = new Item();
                item.setTitle("使用说明");
                item.setDescription("手把手告诉您怎么享用一二三速递的贴心服务");
                item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535683819&di=567a928f300803d6c64ea1f0957f6415&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.hhsn.cn%2Fueditor%2Fphp%2Fupload%2Fimage%2F20171116%2F1510800385166102.jpg");
                item.setPicUrl("http://www.glove1573.cn/help/constact_us.html");
                items.add(item);
                newsMessage.setArticles(items);
                return WechatMessageUtil.newsMessageToXml(newsMessage);
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
