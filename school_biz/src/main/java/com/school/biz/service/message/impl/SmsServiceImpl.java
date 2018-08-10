package com.school.biz.service.message.impl;

import org.springframework.stereotype.Service;

import com.school.biz.service.message.SmsService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 28/06/2018 10:43
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendVerifyCode(String phone, String verifyCode) {
        //TODO
    }
}
