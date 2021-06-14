package com.oracle.sms.dto;

import java.util.ArrayList;
import java.util.List;

public class SurveyResponseDetail {

	Long surveyId;
	Long userId;
	String title;
	String status;
	List<QuestionResponseDetail> responseDetails;
	
	public SurveyResponseDetail(Long surveyId, Long userId, String title) {
		super();
		this.surveyId = surveyId;
		this.userId = userId;
		this.title = title;
		this.responseDetails = new ArrayList<QuestionResponseDetail>();
	}

	public SurveyResponseDetail(Long surveyId, Long userId, String title, String status) {
		super();
		this.surveyId = surveyId;
		this.userId = userId;
		this.title = title;
		this.status = status;
		this.responseDetails = new ArrayList<QuestionResponseDetail>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<QuestionResponseDetail> getResponseDetails() {
		return responseDetails;
	}

	public void setResponseDetails(List<QuestionResponseDetail> responseDetails) {
		this.responseDetails = responseDetails;
	}

}
