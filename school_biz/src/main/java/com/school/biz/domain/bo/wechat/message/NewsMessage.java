package com.school.biz.domain.bo.wechat.message;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/24 10:36
 */
@Data
public class NewsMessage {

    private String MsgType;

    private String ToUserName;

    private String FromUserName;

    private long CreateTime;

    private int ArticleCount;

    private List<Item> Articles = new ArrayList<>();
}
