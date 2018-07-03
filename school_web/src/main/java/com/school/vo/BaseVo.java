package com.school.vo;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
@Validated
public abstract class BaseVo {
    @NotNull(message = "openid参数错误")
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
