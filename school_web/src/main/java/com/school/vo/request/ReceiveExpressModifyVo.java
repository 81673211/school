package com.school.vo.request;

import com.school.vo.BaseVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
@Validated
public class ReceiveExpressModifyVo extends BaseVo {
    @NotNull(message = "参数错误")
    private Long id;
    @NotNull(message = "请选择收件方式")
    private Integer expressWay;    //快件方式，0：自提；1：入柜

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExpressWay() {
        return expressWay;
    }

    public void setExpressWay(Integer expressWay) {
        this.expressWay = expressWay;
    }
}
