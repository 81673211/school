package com.school.web.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.message.SmsService;
import com.school.web.vo.response.Response;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/14 22:57
 */
@Controller
@RequestMapping("/backDoor")
public class BackDoorController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SmsService smsService;

    @RequestMapping("/918")
    @ResponseBody
    public Response send918() throws InterruptedException {
        List<String> phones = customerService.selectRegisteredPhone();
        if (!CollectionUtils.isEmpty(phones)) {
            for (String phone : phones) {
                smsService.sendActivaty918(phone);
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }
        return new Response().writeSuccess("success");
    }
}
