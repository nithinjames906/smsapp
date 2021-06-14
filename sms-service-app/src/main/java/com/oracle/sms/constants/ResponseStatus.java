package com.oracle.sms.constants;

public enum ResponseStatus {
	SUCCESS("Success"), FAILURE("Failure");

	String status;

	private ResponseStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
