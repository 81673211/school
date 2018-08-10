package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author jame
 * @date 2018/8/5
 * @desc 寄件计算vo
 */
@Validated
@Data
public class SendExpressCalcVo {
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
}
