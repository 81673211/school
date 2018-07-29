package com.school.domain.entity.express;

import lombok.Data;

import java.util.Date;

/**
 * @author jame
 */
@Data
public class ExpressReceive extends Express{
    private String senderPhone;
    private String senderName;
    private String senderAddr;
    private Long senderProvinceId;
    private Long senderCityId;
    private Long senderDistrictId;
    private String receiverPhone;
    private String receiverName;
    private String receiverAddr;
    private Date intoBoxTime;

    public Date getIntoBoxTime() {
        return intoBoxTime;
    }

    public void setIntoBoxTime(Date intoBoxTime) {
        this.intoBoxTime = intoBoxTime;
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

}
