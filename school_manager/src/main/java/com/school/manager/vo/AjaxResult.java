package com.school.manager.vo;

import lombok.Data;

@Data
public class AjaxResult {
	
	private boolean ok;
	private String msg;
	private Object data;
	
	public static AjaxResult success() {
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setOk(true);
		return ajaxResult;
	}
	
	public static AjaxResult success(String msg) { 
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setOk(true);
		ajaxResult.setMsg(msg);
		return ajaxResult;
	}
	
	public static AjaxResult success(String msg, Object data) { 
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setOk(true);
		ajaxResult.setMsg(msg);
		ajaxResult.setData(data);
		return ajaxResult;
	}
	
	public static AjaxResult fail() { 
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setOk(false);
		return ajaxResult;
	}
	
	public static AjaxResult fail(String msg) { 
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setOk(false);
		ajaxResult.setMsg(msg);
		return ajaxResult;
	}
	
	public static AjaxResult fail(String msg, Object data) { 
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setOk(false);
		ajaxResult.setMsg(msg);
		ajaxResult.setData(data);
		return ajaxResult;
	}
	
}
