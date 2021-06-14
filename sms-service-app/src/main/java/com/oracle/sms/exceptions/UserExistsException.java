package com.oracle.sms.exceptions;

public class UserExistsException extends SMSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final String msg = "User with same name already exists.";

	public UserExistsException() {
		super(msg);
	}
}
