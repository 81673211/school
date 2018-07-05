package com.school.web;

import com.school.domain.entity.customer.Customer;
import com.school.service.customer.CustomerService;
import com.school.service.wechat.OauthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jame
 */
@Controller
@RequestMapping(value = "/")
public class MenuDecirectController {

    @Autowired
    private OauthService oauthService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("express/send/history")
    public ModelAndView sendExpressHistory(@RequestParam(value = "code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isBlank(code)) {
            modelAndView.setViewName("redirect:/error");
        } else {
            Customer customer = customerService.getByOpenId(oauthService.getOAuthToken(code).getOpenId());
            modelAndView.setViewName("redirect:/express/0/list?openId=" + customer.getOpenId());
        }
        return modelAndView;
    }

    @RequestMapping("express/send")
    public ModelAndView sendExpress(@RequestParam(value = "code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isBlank(code)) {
            modelAndView.setViewName("redirect:/error");
        } else {
            Customer customer = customerService.getByOpenId(oauthService.getOAuthToken(code).getOpenId());
            modelAndView.setViewName("redirect:/express/sending?openid=" + customer.getOpenId());
        }
        return modelAndView;
    }

    @RequestMapping("error")
    public ModelAndView error() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
