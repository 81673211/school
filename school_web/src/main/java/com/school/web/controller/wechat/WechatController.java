package com.school.web.controller.wechat;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.biz.domain.bo.wechat.OAuthToken;
import com.school.biz.domain.bo.wechat.UserWechat;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.wechat.EventService;
import com.school.biz.service.wechat.OauthService;
import com.school.biz.util.wechat.WechatMessageUtil;
import com.school.web.util.WechatSignatureCheckUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 11:17
 */
@Controller
@RequestMapping("/wx")
@Slf4j
public class WechatController {

    @Autowired
    private EventService eventService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST)
    public void eventHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> paramMap;
        try {
            paramMap = WechatMessageUtil.parseXml(request.getInputStream());
        } catch (Exception e) {
            log.error("微信事件通知request转map异常,{}", e.getMessage());
            throw new RuntimeException(e);
        }
        String result = eventService.process(paramMap);
        log.info("result:{}", result);
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
        if (echostr != null && WechatSignatureCheckUtil.checkSignature(signature, timestamp, nonce)) {
            PrintWriter out = response.getWriter();
            out.println(echostr);
            out.close();
        }
    }

    @RequestMapping(value = "/proxy", method = RequestMethod.GET)
    public ModelAndView proxy(String code, String state) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        String decodeState = URLDecoder.decode(state, "UTF-8");
        OAuthToken oAuthToken = oauthService.getOAuthToken(code);
        String openId = oAuthToken.getOpenId();
        Customer customer = customerService.getByOpenId(openId);
        if (customer == null) {
            customer = customerService.create(openId);
        }
        if (StringUtils.isBlank(customer.getAvatar())) {
            UserWechat userWechat = oauthService.getDetail(openId, oAuthToken.getAccessToken());
            customer.setAvatar(userWechat.getAvatar());
            customer.setSex(String.valueOf(userWechat.getSex()));
            customerService.update(customer);
        }
        String viewName;
        if (state.contains("?")) {
            viewName = "redirect:" + decodeState + "&openId=" + openId;
        } else {
            viewName = "redirect:" + decodeState + "?openId=" + openId;
        }
        mav.setViewName(viewName);
        return mav;
    }

}
