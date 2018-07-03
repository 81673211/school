package com.school.vo.request;

import com.school.vo.BaseVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
@Validated
public class OrderCreateVo extends BaseVo{
    @NotNull(message = "参数错误")
    private Long expressId;

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }
}
