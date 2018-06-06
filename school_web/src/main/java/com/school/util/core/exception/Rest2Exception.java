/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.school.util.core.exception;

import org.springframework.http.HttpStatus;

/**
 * 专用于Restful Service的异常.
 * 
 * @author
 */
public class Rest2Exception extends RuntimeException {

	public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

	public Rest2Exception() {
	}

	public Rest2Exception(HttpStatus status) {
		this.status = status;
	}

	public Rest2Exception(String message) {
		super(message);
	}

	public Rest2Exception(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
}
