package com.oracle.sms.dto;

import java.util.ArrayList;
import java.util.List;

public class SurveyResponseRequest {

	Long surveyId;
	Long userId;
	List<UserSurveyResponse> surveyResponses;

	public SurveyResponseRequest() {
		super();
	}

	public SurveyResponseRequest(Long surveyId, Long userId) {
		super();
		this.surveyId = surveyId;
		this.userId = userId;
		this.surveyResponses = new ArrayList<UserSurveyResponse>();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public List<UserSurveyResponse> getSurveyResponses() {
		return surveyResponses;
	}

	public void setSurveyResponses(List<UserSurveyResponse> surveyResponses) {
		this.surveyResponses = surveyResponses;
	}

}