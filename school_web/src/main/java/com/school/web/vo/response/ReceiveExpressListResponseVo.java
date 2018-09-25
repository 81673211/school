package com.school.web.vo.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author jame
 */
@Data
public class ReceiveExpressListResponseVo {
    private Long id;
    private String code;
    private Long companyId;
    private String companyName;
    private Integer expressStatus;
    private String senderPhone;
    private String senderName;
    private String senderAddr;
    private String expressStatusDesc;
    private Date endTime;
    private Date createdTime;
    private String senderProvince;
    private String senderCity;
    private String senderDistrict;
    private BigDecimal distributionBoxCost;
    private BigDecimal distributionDoorCost;
    private Integer expressType;
    private Integer expressWay;
    private BigDecimal reOrderAmt;
}
