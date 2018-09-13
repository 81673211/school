package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author jame
 */
@Validated
@Data
public class ReceiveSerivceReOrderCreateVo {
    @NotNull(message = "快递ID不能为空")
    private Long expressId;
    @NotNull(message = "补单类型不能为空")
    private String type;
    @NotNull(message = "openId不能为空")
    private String openId;

}
