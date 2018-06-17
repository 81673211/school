package com.school.web.wechat;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.school.service.wechat.EventService;
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
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    public void eventHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = eventService.process(request);
        LOGGER.info("result:{}", result);

        PrintWriter out = response.getWriter();
        out.println(result);
        out.close();
    }

    @RequestMapping(method = RequestMethod.GET)
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
    }

}