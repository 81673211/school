package com.school.service.wechat.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.service.customer.CustomerService;
import com.school.service.wechat.OauthService;
import com.school.service.wechat.WechatEventService;
import com.school.util.Constants;
import com.school.util.wechat.OAuthToken;
import com.school.util.wechat.WechatEventTypeEnum;
import com.school.web.wechat.WechatController;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 16:29
 */
@Service
public class WechatEventServiceImpl implements WechatEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OauthService oauthService;

    @Override
    public String process(HttpServletRequest request) throws Exception {
        Map<String, String> paramMap = parseXml(request);
        LOGGER.info("paramMap:{}", paramMap.toString());
        String event = paramMap.get("Event");
        LOGGER.info("event:{}", event);
        //TODO
        if ("unsubscribe".equals(event)) {

        } else if ("subscribe".equals(event)) {
            String openId = paramMap.get("FromUserName");
            customerService.subscribe(openId);
            String oauthTokenStr = redisTemplate.opsForValue().get(
                    Constants.CACHE_NAMESPACE_OAUTH_TOKEN.replace("${openId}", openId));
            if (oauthTokenStr == null) {
                //未授权过，从头获取code & oauth, then cache it
                String oauthUrl = oauthService.getOAuthUrl();
                LOGGER.info("oauthUrl:{}", oauthUrl);
//                response.sendRedirect("http://www.glove1573.cn");
            } else {
                OAuthToken oAuthToken = JSON.parseObject(oauthTokenStr, OAuthToken.class);
                if (oAuthToken.isExpired()) {

                }
                //token已过期，refresh it
                //token未过期，直接请求用户信息
            }
        }
        return null;
    }

    private String unsubscribe(String openId) {
        return null;
    }

    private String subscribe(String openId) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();

        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        String requestXml = document.asXML();
        String subXml = requestXml.split(">")[0] + ">";
        requestXml = requestXml.substring(subXml.length());

        // 得到xml根元素
        Element root = document.getRootElement();

        // 得到根元素的全部子节点
        List<Element> elementList = root.elements();

        // 遍历全部子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        map.put("requestXml", requestXml);
        // 释放资源
        inputStream.close();
        return map;

    }
}
