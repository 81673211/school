package com.school.biz.domain.entity.resource;

public class ResourceInfoVo {
    private Long id;

    private String resName;

    private String resUrl;

    private Long parentResId;
    
    private String parentResName;

    private Long menuId;
    
    private String menuName;

    private String resRemark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl == null ? null : resUrl.trim();
    }

    public Long getParentResId() {
        return parentResId;
    }

    public void setParentResId(Long parentResId) {
        this.parentResId = parentResId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getResRemark() {
        return resRemark;
    }

    public void setResRemark(String resRemark) {
        this.resRemark = resRemark == null ? null : resRemark.trim();
    }

	public String getParentResName() {
		return parentResName;
	}

	public void setParentResName(String parentResName) {
		this.parentResName = parentResName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
}