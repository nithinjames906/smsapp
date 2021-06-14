package com.oracle.sms.exceptions;

public class UserSurveyNotFound extends SMSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final String msg = "UserSurvey not found.";

	public UserSurveyNotFound() {
		super(msg);
	}

	public UserSurveyNotFound(Long surveyId, Long userId) {
		super(msg + " surveyId -> " + surveyId + " userId-> " + userId);
	}
}
