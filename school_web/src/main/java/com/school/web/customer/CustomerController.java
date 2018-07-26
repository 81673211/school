package com.school.web.customer;

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
            customerService.update(request);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "/verifyCode")
    @ResponseBody
    public String sendVerifyCode(String phone) {
        customerService.sendVerifyCode(phone);
        return "1";
    }
}
