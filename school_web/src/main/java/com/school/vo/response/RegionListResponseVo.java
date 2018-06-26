package com.school.vo.response;

import com.school.vo.BaseVo;

/**
 * @author jame
 */
public class RegionListResponseVo extends BaseVo{
    private Long id;
    private String areaName;
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
