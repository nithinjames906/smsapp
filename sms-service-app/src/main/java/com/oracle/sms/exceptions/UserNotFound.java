package com.oracle.sms.exceptions;

public class UserNotFound extends SMSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String msg = "SMS user not found.";

	public UserNotFound() {
		super(msg);
	}

}
