package com.school.biz.exception;

/**
 * 未初始化异常
 * @author
 */
public class UtilException extends RuntimeException{
	private static final long serialVersionUID = 8247610319171014183L;

	public UtilException(String message) {
		super(message);
	}
	
	public UtilException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
