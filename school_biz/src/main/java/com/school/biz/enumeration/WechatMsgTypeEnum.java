package com.school.biz.enumeration;

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
    IMAGE("image", "图片"),
    VOICE("voice", "语音"),
    EVENT("event", "事件"),
    NEWS("news", "新闻"),
    VIDEO("video", "视频");

    private String code;

    private String message;
}
