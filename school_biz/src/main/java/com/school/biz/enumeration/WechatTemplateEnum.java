package com.school.biz.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 21:51
 */
@AllArgsConstructor
@Getter
public enum WechatTemplateEnum {

    RECEIVE_EXPRESS_ARRIVAL("wx_template_id_0", "收件快递已到达代收点"),
    RECEIVE_EXPRESS_DISTRIBUTION_SELF("wx_template_id_1", "收件快递自提"),
    RECEIVE_EXPRESS_FINISH("wx_template_id_2", "收件快递完成"),
    SEND_EXPRESS_ARRIVAL_ALERT("wx_template_id_3", "寄件订单处理提醒"),
    RECEIVE_EXPRESS_ARRIVAL_ALERT("wx_template_id_4", "收件订单处理提醒"),
    RECEIVE_EXPRESS_DISTRIBUTION_ALERT("wx_template_id_5", "收件配送通知");

    private String type;
    private String message;
}
