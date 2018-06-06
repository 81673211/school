package com.school.util.core.pojo;

public class ReqHeader implements java.io.Serializable{


	private static final long serialVersionUID = -4848442659867001167L;

	//protected String version;

	protected String token;// 用户Token，登录时下发
    
	protected String os;
	
	//每页显示条数
	protected Integer pagesize = 20;
	//页码
	protected Integer pagenum = 1;
	
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getPagenum() {
		return pagenum;
	}

	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}
	
	/*public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}*/

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

    
	
	
}
