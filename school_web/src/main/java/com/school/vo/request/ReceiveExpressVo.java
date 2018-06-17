package com.school.vo.request;

import com.school.vo.BaseVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
@Validated
public class ReceiveExpressVo extends BaseVo {
    private Long id;
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
    @NotNull(message = "寄件人省份不为空")
    private Long senderProvinceId;
    @NotNull(message = "寄件人城市不为空")
    private Long senderCityId;
    @NotNull(message = "寄件人区县不为空")
    private Long senderDistrictId;
    @NotNull(message = "快递公司编号不为空")
    private Long companyId;    //快递公司
    @NotNull(message = "收件类型不为空")
    private Integer expressWay;    //快件方式，0：自提；1：入柜

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

    public Long getSenderProvinceId() {
        return senderProvinceId;
    }

    public void setSenderProvinceId(Long senderProvinceId) {
        this.senderProvinceId = senderProvinceId;
    }

    public Long getSenderCityId() {
        return senderCityId;
    }

    public void setSenderCityId(Long senderCityId) {
        this.senderCityId = senderCityId;
    }

    public Long getSenderDistrictId() {
        return senderDistrictId;
    }

    public void setSenderDistrictId(Long senderDistrictId) {
        this.senderDistrictId = senderDistrictId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getExpressWay() {
        return expressWay;
    }

    public void setExpressWay(Integer expressWay) {
        this.expressWay = expressWay;
    }
}
