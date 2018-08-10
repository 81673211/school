package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author jame
 */
@Validated
@Data
public class OrderCreateVo {
    @NotNull(message = "参数错误")
    private Long expressId;
    @NotNull(message = "openId不能为空")
    private String openId;
}
