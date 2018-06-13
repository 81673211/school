package com.school.util.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 17:24
 */
@AllArgsConstructor
@Getter
public enum WechatEventTypeEnum {

    SUBSCRIBE("subscribe", "关注"),
    UNSUBSCRIBE("unsubscribe", "取消关注"),
    CLICK("CLICK", "点击"),
    SCAN("SCAN", "二维码扫描");

    private String code;

    private String message;
}
