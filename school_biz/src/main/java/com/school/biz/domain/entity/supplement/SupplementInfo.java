package com.school.biz.domain.entity.supplement;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jame
 * @date 2018/9/10
 * @desc description
 */
@Data
public class SupplementInfo {
    private Long id;
    private BigDecimal amount;
    private Integer expressType;
    private Long expressId;
    private Integer type;
    private Integer isPay;
    private Date createdTime;
    private Integer isDeleted;
    
    public SupplementInfo() {
    	super();
    }

	public SupplementInfo(BigDecimal amount, Integer expressType, Long expressId, Integer type, Date createdTime) {
		super();
		this.amount = amount;
		this.expressType = expressType;
		this.expressId = expressId;
		this.type = type;
		this.createdTime = createdTime;
	}
    
}
