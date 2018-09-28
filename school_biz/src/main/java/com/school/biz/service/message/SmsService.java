package com.school.biz.service.message;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 28/06/2018 10:43
 */
public interface SmsService {

    void sendVerifyCode(String phone, String verifyCode);

    void sendActivaty918(String phone);

    void sendReceiveExpressCreatedSms(String phone, String name);
}
