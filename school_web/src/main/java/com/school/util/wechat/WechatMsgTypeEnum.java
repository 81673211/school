package com.school.util.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 17:28
 */
@AllArgsConstructor
@Getter
public enum WechatMsgTypeEnum {

    TEXT("text", "文本"),
    EVENT("event", "事件");

    private String code;

    private String message;
}
