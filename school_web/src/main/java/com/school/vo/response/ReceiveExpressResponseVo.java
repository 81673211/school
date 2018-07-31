package com.school.vo.response;

import com.school.vo.BaseVo;

/**
 * @author jame
 */
public class ReceiveExpressResponseVo extends BaseVo{
    private Long id;
    private String code; //快件编号
    private String receiverPhone;
    private String receiverName;
    private String senderPhone;
    private String senderName;
    private String senderAddr;
    private Long senderProvinceId;
    private Long senderCityId;
    private Long senderDistrictId;
    private Long companyId;    //快递公司
    private String companyCode; //快递code
    private String companyName; //快递name
    private Integer expressStatus; //快件状态
    private String createdTime; //创建时间

    private String senderProvince;
    private String senderCity;
    private String senderDistrict;

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

    public Integer getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(Integer expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderDistrict() {
        return senderDistrict;
    }

    public void setSenderDistrict(String senderDistrict) {
        this.senderDistrict = senderDistrict;
    }
}
