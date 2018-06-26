package com.school.vo.response;

import com.school.vo.BaseVo;

/**
 * @author jame
 */
public class ReceiveExpressListResponseVo extends BaseVo {
    private Long id;
    private String code;
    private String companyName;
    private Integer expressStatus;
    private String senderPhone;
    private String senderName;
    private String senderAddr;
    private String expressStatusDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddr() {
        return senderAddr;
    }

    public void setSenderAddr(String senderAddr) {
        this.senderAddr = senderAddr;
    }

    public String getExpressStatusDesc() {
        return expressStatusDesc;
    }

    public void setExpressStatusDesc(String expressStatusDesc) {
        this.expressStatusDesc = expressStatusDesc;
    }
}
