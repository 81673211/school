package com.school.web.customer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.school.service.customer.CustomerService;
import com.school.service.wechat.OauthService;
import com.school.web.base.BaseEasyWebController;
import com.school.web.customer.request.CustomerProfileEditRequest;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 17:22
 */
@Controller
@RequestMapping("/customer")
@Slf4j
public class CustomerController extends BaseEasyWebController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OauthService oauthService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@RequestParam(value = "code", required = false) String code,
                                @RequestParam(value = "openId", required = false) String openId) {
        ModelAndView mav = new ModelAndView("profile");
        if (StringUtils.isNotBlank(openId)) {
            mav.addObject("customer", customerService.getByOpenId(openId));
        } else {
            mav.addObject("customer", customerService.getByOpenId(oauthService.getOAuthToken(code).getOpenId()));
        }
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public String profile(CustomerProfileEditRequest request) {
        customerService.update(request);
        return "success";
    }

    @RequestMapping(value = "/verifyCode")
    @ResponseBody
    public String sendVerifyCode(String phone) {
        customerService.sendVerifyCode(phone);
        return "1";
    }

}
