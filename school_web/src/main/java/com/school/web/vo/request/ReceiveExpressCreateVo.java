package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * @author jame
 */
@Validated
public class ReceiveExpressCreateVo {
    private Long id;
    @NotNull(message = "快递单号不为空")
    private String code;
    @NotNull(message = "收件人联系电话不为空")
    private String receiverPhone;
    @NotNull(message = "收件人姓名不为空")
    private String receiverName;
    @NotNull(message = "寄件人电话不为空")
    private String senderPhone;
    @NotNull(message = "寄件人名字不为空")
    private String senderName;
    @NotNull(message = "寄件人地址不为空")
    private String senderAddr;
    private Long companyId;    //快递公司
    private String companyCode;    //快递公司
    private String companyName;    //快递公司

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderAddr() {
        return senderAddr;
    }

    public void setSenderAddr(String senderAddr) {
        this.senderAddr = senderAddr;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
