package com.school.biz.domain.entity.security;

import java.util.List;

/** 
 * @author  作者：方梁
 * @date 创建时间：2016年12月13日 下午4:01:45
 * @description   左侧菜单栏
 */
public class LeftMenu {
	private Long id;
    /*
     * 父级菜单id
     */
    private Long pid;
    /*
     * 资源链接
     */
    private String resourceUrl;
    /*
     * 资源描述
     */
    private String resourceName;
    /*
     * 排序
     */
    private Integer mindex;
    /*
     * 所在层级
     */
    private Integer level;
    /*
	 * 是否是叶子节点 0:否 1:是
	 */
	private Integer leaf;
	/*
	 * 菜单图标
	 */
	private String icon;
	private Long userId;
	private Long companyId;
	private List<LeftMenu> menuList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Integer getMindex() {
		return mindex;
	}
	public void setMindex(Integer mindex) {
		this.mindex = mindex;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public List<LeftMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<LeftMenu> menuList) {
		this.menuList = menuList;
	}
}
