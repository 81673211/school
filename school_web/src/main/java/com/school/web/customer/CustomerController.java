package com.school.web.customer;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.school.service.customer.CustomerService;
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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@RequestParam(value = "openId", required = false) String openId) {
        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("customer", customerService.getByOpenId(openId));
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public String profile(CustomerProfileEditRequest request) {
        try {
            if (StringUtils.isBlank(request.getOpenId())) {
                throw new RuntimeException("openId丢失");
            }
            if (StringUtils.isBlank(request.getAddr())) {
                throw new RuntimeException("地址不能为空");
            }
            customerService.update(request);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "/verifyCode")
    @ResponseBody
    public String sendVerifyCode(String phone) {
        try {
            if (StringUtils.isBlank(phone)) {
                throw new RuntimeException("手机号不能为空");
            }
            if (!Pattern.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$", phone)) {
                throw new RuntimeException("手机号格式错误");
            }
            customerService.sendVerifyCode(phone);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "1";
    }
}
