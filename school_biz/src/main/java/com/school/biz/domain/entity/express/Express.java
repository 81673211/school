package com.school.biz.domain.entity.express;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author jame
 */
@Data
public class Express {
    private Long id;
    private String code;
    private Long companyId;
    private String companyCode;
    private String companyName;
    private Date createdTime;
    private Boolean isDeleted;
    private Integer expressStatus;
    private Integer expressWay;//快件方式：入柜/自己
    private Long customerId;
    private Date endTime;
    private BigDecimal serviceAmt;
    private Integer expressType;
    private BigDecimal expressWeight;
    private String remark;
}
