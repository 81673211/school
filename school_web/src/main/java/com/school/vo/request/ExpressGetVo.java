package com.school.vo.request;

import com.school.vo.BaseVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
@Validated
public class ExpressGetVo extends BaseVo{
    @NotNull(message = "参数错误")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
