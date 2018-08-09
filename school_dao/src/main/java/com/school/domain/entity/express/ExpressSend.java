package com.school.domain.entity.express;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author jame
 */
public class ExpressSend extends Express{
    private String senderPhone;
    private String senderName;
    private String senderAddr;
    private String receiverPhone;
    private String receiverName;
    private String receiverAddr;
    private Long receiverProvinceId;
    private Long receiverCityId;
    private Long receiverDistrictId;

    @JSONField(serialize = false)
    private String receiverProvince;
    @JSONField(serialize = false)
    private String receiverCity;
    @JSONField(serialize = false)
    private String receiverDistrict;


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


    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }
}
