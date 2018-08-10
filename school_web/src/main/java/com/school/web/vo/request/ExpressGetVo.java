package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * @author jame
 */
@Validated
public class ExpressGetVo {
    @NotNull(message = "参数错误")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
