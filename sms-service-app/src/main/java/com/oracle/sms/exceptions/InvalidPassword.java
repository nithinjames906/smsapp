package com.oracle.sms.exceptions;

public class InvalidPassword extends SMSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String msg = "Invalid password provided.";

	public InvalidPassword() {
		super(msg);
	}

}
