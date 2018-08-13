package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author jame
 */
@Validated
@Data
public class ReceiveExpressModifyVo {
    @NotNull(message = "openId不能为空")
    private String openId;
    @NotNull(message = "参数错误")
    private Long id;
    @NotNull(message = "请选择收件方式")
    private Integer expressWay;    //快件方式，0：自提；1：入柜
}
