package com.school.domain.dto.good;

import java.io.Serializable;

public class CacheGoodTypeDto implements Serializable {

	/**    
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
	 *    
	 * @since Ver 1.1    
	 */    
	
	private static final long serialVersionUID = 1L;

	private Long id;

    private String typeName;

    private Integer status;

    private String picPath;

    private Long pid;

    private Integer sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
    
}
