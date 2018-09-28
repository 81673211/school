package com.school.biz.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.WechatTemplateEnum;
import com.school.biz.service.message.SmsService;
import com.school.biz.service.wechat.TemplateService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/28 20:38
 */
@Component
public class MessageHelper {

    @Autowired
    private SmsService smsService;

    @Autowired
    private TemplateService templateService;

    public void sendReceiveExpressCreatedMessage(Customer customer, ExpressReceive expressReceive) {
        if (customer == null || !customer.isSubscribed() || StringUtils.isBlank(customer.getPhone())) {
            smsService.sendReceiveExpressCreatedSms(expressReceive.getReceiverPhone(), expressReceive.getReceiverName());
        } else {
            templateService.send(WechatTemplateEnum.RECEIVE_EXPRESS_ARRIVAL.getType(), customer.getOpenId(),
                                 expressReceive, ExpressTypeEnum.RECEIVE.getFlag());
        }
    }

}
