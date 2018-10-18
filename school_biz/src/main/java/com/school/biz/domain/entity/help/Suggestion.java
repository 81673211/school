package com.school.biz.domain.entity.help;

import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/20 16:33
 */
@Data
public class Suggestion {

    private Long id;

    private Long customerId;

    private String customerName;

    private String customerPhone;

    private String openId;

    private String content;

    private Date createdTime;
}
