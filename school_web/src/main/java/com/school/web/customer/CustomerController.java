package com.school.web.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.school.service.customer.CustomerService;
import com.school.web.base.BaseEasyWebController;

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

    @RequestMapping("/profile")
    public void edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("-------------/customer/profile/profile---------");
        String code = request.getParameter("code");
        log.info("-------------code---------:{}", code);
        response.sendRedirect("/profile.html");
    }

    @RequestMapping("/test")
    public void test(String openId) {
        customerService.subscribe(openId);
    }


}
