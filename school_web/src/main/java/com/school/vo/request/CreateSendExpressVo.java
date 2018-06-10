package com.school.vo.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
public class CreateSendExpressVo extends BaseVo {
    @NotNull(message = "寄件人联系电话不为空")
    private String senderPhone;//	寄件人联系电话
    @NotNull(message = "寄件人姓名不为空")
    private String senderName;        //寄件人姓名
    @NotNull(message = "收件人电话不为空")
    private String receiverPhone;    //收件人电话
    @NotNull(message = "收件人名字不为空")
    private String receiverName;//	收件人名字
    @NotNull(message = "收件人地址不为空")
    private String receiverAddr;//	收件人地址
    private String receiverProvinceId;    //收件人省份ID
    private String receiverCityId;//收件人城市ID
    private String receiverDistrictId;        //收件人区县ID
    @NotNull(message = "快递公司编号不为空")
    private String companyId;    //快递公司
    @Min(value = 0, message = "金额不能小于零")
    private Double amount;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public String getReceiverProvinceId() {
        return receiverProvinceId;
    }

    public void setReceiverProvinceId(String receiverProvinceId) {
        this.receiverProvinceId = receiverProvinceId;
    }

    public String getReceiverCityId() {
        return receiverCityId;
    }

    public void setReceiverCityId(String receiverCityId) {
        this.receiverCityId = receiverCityId;
    }

    public String getReceiverDistrictId() {
        return receiverDistrictId;
    }

    public void setReceiverDistrictId(String receiverDistrictId) {
        this.receiverDistrictId = receiverDistrictId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
