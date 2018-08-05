package com.school.vo.request;

import com.school.vo.BaseVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 * @date 2018/8/5
 * @desc 寄件计算vo
 */
@Validated
public class SendExpressCalcVo extends BaseVo{
    @NotNull(message = "收件人地址不为空")
    private String receiverAddr;//	收件人地址
    @NotNull(message = "收件人省份不为空")
    private Long receiverProvinceId;    //收件人省份ID
    @NotNull(message = "收件人城市不为空")
    private Long receiverCityId;//收件人城市ID
    @NotNull(message = "收件人区县不为空")
    private Long receiverDistrictId;        //收件人区县ID
    @NotNull(message = "快递公司不为空")
    private Long companyId;    //快递公司

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
}
