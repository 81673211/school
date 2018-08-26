package com.school.web.vo.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * @author jame
 */
@Validated
public class ExpressStatusModifyVo {
    @NotNull(message = "参数错误")
    private Long id;
    @NotNull(message = "参数错误")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
