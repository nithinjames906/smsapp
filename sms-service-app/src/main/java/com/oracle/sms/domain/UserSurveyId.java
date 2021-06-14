package com.oracle.sms.domain;

import java.io.Serializable;

public class UserSurveyId implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Long surveyId;

	private Long userId;

	public UserSurveyId() {
		super();
	}

	public UserSurveyId(Long surveyId, Long userId) {
		super();
		this.surveyId = surveyId;
		this.userId = userId;
	}

}
