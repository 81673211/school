package com.school.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:快递类型枚举.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:ExpressTypeEnum</b>
 * <br><b>Date:</b> 07/06/2018 14:55
 */
@AllArgsConstructor
@Getter
public enum ExpressTypeEnum {

    SEND("send", "寄件"),
    RECEIVE("receive", "收件");

    private String code;

    private String message;
}
