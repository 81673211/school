package com.school.web.vo.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

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
    @NotNull
    private String type;

}
