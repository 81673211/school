package com.school.vo.request;

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
    @NotNull(message = "收件人省份不为空")
    private Long receiverProvinceId;    //收件人省份ID
    @NotNull(message = "收件人城市不为空")
    private Long receiverCityId;//收件人城市ID
    @NotNull(message = "收件人区县不为空")
    private Long receiverDistrictId;        //收件人区县ID
    @NotNull(message = "快递公司编号不为空")
    private Long companyId;    //快递公司

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getReceiverProvinceId() {
        return receiverProvinceId;
    }

    public void setReceiverProvinceId(Long receiverProvinceId) {
        this.receiverProvinceId = receiverProvinceId;
    }

    public Long getReceiverCityId() {
        return receiverCityId;
    }

    public void setReceiverCityId(Long receiverCityId) {
        this.receiverCityId = receiverCityId;
    }

    public Long getReceiverDistrictId() {
        return receiverDistrictId;
    }

    public void setReceiverDistrictId(Long receiverDistrictId) {
        this.receiverDistrictId = receiverDistrictId;
    }
}
