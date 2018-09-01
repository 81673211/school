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
    SEND_EXPRESS_FINISH("wx_template_id_3", "寄件快递完成");

    private String type;
    private String message;
}
