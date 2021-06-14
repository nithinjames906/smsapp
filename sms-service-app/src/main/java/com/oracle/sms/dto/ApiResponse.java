package com.oracle.sms.dto;

import java.util.Collections;

import com.oracle.sms.constants.ResponseStatus;

public class ApiResponse {

	String status;
	String message;
	Object data;

	public ApiResponse() {
		super();
		this.status = ResponseStatus.SUCCESS.getStatus();
		this.message = "";
		this.data = Collections.emptyList();
	}

	public ApiResponse(String status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	public ApiResponse(String status, String message, Object data) {
		this(status, message);
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
