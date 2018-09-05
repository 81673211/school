package com.school.biz.domain.entity.log;

import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/4 21:58
 */
@Data
public class ExpressLog {

    private Long id;

    private Long expressId;

    private int expressType;

    private String action;

    private String preStatus;

    private String postStatus;

    private String remark;

    private Long operatorId;

    private String operatorName;

    private Date operateTime;
}
