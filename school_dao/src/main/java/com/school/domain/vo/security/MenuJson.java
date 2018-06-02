package com.school.domain.vo.security;

import lombok.Data;

import java.util.List;

public class MenuJson {
	private Long id;
	private Long pId;
	private String name;
	private Long resId;
	private boolean checked;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
