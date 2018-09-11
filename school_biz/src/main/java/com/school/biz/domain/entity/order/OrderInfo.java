package com.school.biz.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Long id;

    private String orderNo;

    private Long customerId;

    private Integer expressType;

    private Long expressId;

    private String expressCode;

    private BigDecimal amount;

    private BigDecimal refundAmt;

    private Date createdTime;

    private String tradeSummary;

    private String merchParam;

    private Integer status;

    private String notifyUrl;

    private Date sucTime;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getExpressType() {
        return expressType;
    }

    public void setExpressType(Integer expressType) {
        this.expressType = expressType;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getTradeSummary() {
        return tradeSummary;
    }

    public void setTradeSummary(String tradeSummary) {
        this.tradeSummary = tradeSummary == null ? null : tradeSummary.trim();
    }

    public String getMerchParam() {
        return merchParam;
    }

    public void setMerchParam(String merchParam) {
        this.merchParam = merchParam == null ? null : merchParam.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public Date getSucTime() {
        return sucTime;
    }

    public void setSucTime(Date sucTime) {
        this.sucTime = sucTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}