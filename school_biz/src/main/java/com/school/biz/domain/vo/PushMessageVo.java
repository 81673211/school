package com.school.biz.domain.vo;

import com.school.biz.enumeration.PushMessageEnum;
import lombok.Data;

/**
 * @author jame
 * @date 2018/9/9
 * @desc description
 */
@Data
public class PushMessageVo {
    private String openId;
    private PushMessageEnum desc;
    private String createTime;
}
