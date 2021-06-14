package com.oracle.sms.exceptions;

public class SurveyNotFound extends SMSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final String msg = "Survey not found.";

	public SurveyNotFound() {
		super(msg);
	}

	public SurveyNotFound(Long id) {
		super(msg + " id ->" + id);
	}

}
