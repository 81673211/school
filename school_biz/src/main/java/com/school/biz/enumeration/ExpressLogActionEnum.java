package com.school.biz.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 21:07
 */
@AllArgsConstructor
@Getter
public enum ExpressLogActionEnum {

    RECEIVE_EXPRESS_CREATE("创建收件"),
    RECEIVE_EXPRESS_UPDATE("更新收件"),
    RECEIVE_EXPRESS_DEL("删除收件"),
    SEND_EXPRESS_CREATE("创建寄件"),
    SEND_EXPRESS_UPDATE("更新寄件"),
    SEND_EXPRESS_DEL("删除寄件");

    private String msg;
}
