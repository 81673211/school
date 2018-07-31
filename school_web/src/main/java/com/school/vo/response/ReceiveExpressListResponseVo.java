package com.school.vo.response;

import java.util.Date;

import com.school.vo.BaseVo;

import lombok.Data;

/**
 * @author jame
 */
@Data
public class ReceiveExpressListResponseVo extends BaseVo {
    private Long id;
    private String code;
    private String companyName;
    private Integer expressStatus;
    private String senderPhone;
    private String senderName;
    private String senderAddr;
    private String expressStatusDesc;
    private String endTime;
    private String senderProvince;
    private String senderCity;
    private String senderDistrict;
    private Date createdTime;
}
