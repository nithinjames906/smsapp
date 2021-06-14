package com.oracle.sms.dto;

public class UpdateSurveyRequest extends AddSurveyRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long surveyId;

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

}
