package com.school.util.core.pojo;

public class ReqEntity<T> implements java.io.Serializable {
	
	private static final long serialVersionUID = -4848442659867001167L;
	
	private ReqHeader header;
	
	private T body;

	public ReqHeader getHeader() {
		return header;
	}

	public void setHeader(ReqHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
}
