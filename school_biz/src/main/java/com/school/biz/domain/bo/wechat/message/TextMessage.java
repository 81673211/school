package com.school.biz.domain.bo.wechat.message;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 10:21
 */
@Data
public class TextMessage {

    private String MsgType;

    private String ToUserName;

    private String FromUserName;

    private long CreateTime;

    private String Content;
}
