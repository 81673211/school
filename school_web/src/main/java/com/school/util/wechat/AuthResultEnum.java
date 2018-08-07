package com.school.util.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 08:49
 */
@AllArgsConstructor
@Getter
public enum AuthResultEnum {

    SUCCESS(0, "成功"),
    EXPIRE(1, "超时"),
    FAIL(2, "失败");

    private int code;

    private String message;
}
