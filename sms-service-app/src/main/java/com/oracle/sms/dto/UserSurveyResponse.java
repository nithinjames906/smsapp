package com.oracle.sms.dto;

import java.util.List;

public class UserSurveyResponse {

	Long questionId;
	List<String> responseList;

	public UserSurveyResponse(Long questionId, List<String> responseList) {
		super();
		this.questionId = questionId;
		this.responseList = responseList;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public List<String> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<String> responseList) {
		this.responseList = responseList;
	}

}
