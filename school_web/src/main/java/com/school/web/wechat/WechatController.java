package com.school.web.wechat;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.service.customer.CustomerService;
import com.school.util.wechat.CheckUtil;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 11:17
 */
@Controller
@RequestMapping("/wx")
public class WechatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping
    @ResponseBody
    public void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean isGet = request.getMethod().equalsIgnoreCase("get");
        if (isGet) {
            // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");

            String echostr = request.getParameter("echostr");

            if (echostr != null && CheckUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.println(echostr);
                out.close();
            }
        } else {
            Map<String, String> paramMap = parseXml(request);
            String event = paramMap.get("Event");
            if ("unsubscribe".equals(event)) {

            } else if ("subscribe".equals(event)) {
                customerService.subscribe(paramMap.get("FromUserName"));
            }
        }
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
