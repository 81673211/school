package com.school.vo.response;

import com.school.vo.BaseVo;

import java.util.Date;

/**
 * @author jame
 */
public class SendExpressResponseVo extends BaseVo{
    private Long id;
    private String code; //快件编号
    private String senderPhone;//	寄件人联系电话
    private String senderName;        //寄件人姓名
    private String receiverPhone;    //收件人电话
    private String receiverName;//	收件人名字
    private String receiverAddr;//	收件人地址
    private Long receiverProvinceId;    //收件人省份ID
    private Long receiverCityId;//收件人城市ID
    private Long receiverDistrictId;        //收件人区县ID
    private Long companyId;    //快递公司
    private String companyCode; //快递code
    private String companyName; //快递name
    private Integer expressStatus; //快件状态
    private Date createdTime; //创建时间
    private Date endTime;


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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
