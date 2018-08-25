package com.school.biz.util.wechat;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.school.biz.domain.bo.wechat.message.Item;
import com.school.biz.domain.bo.wechat.message.NewsMessage;
import com.school.biz.domain.bo.wechat.message.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 10:19
 */
public final class WechatMessageUtil {

    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.aliasType("item", Item.class);
        xstream.alias("xml", newsMessage.getClass());
        return xstream.toXML(newsMessage);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(InputStream inputStream) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();

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
