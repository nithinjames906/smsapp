package com.oracle.sms.dto;

import java.util.List;

public class QuestionResponseDetail {

	QuestionDetail questionDetail;
	List<String> responseList;

	public QuestionResponseDetail(QuestionDetail questionDetail, List<String> responseList) {
		super();
		this.questionDetail = questionDetail;
		this.responseList = responseList;
	}

	public QuestionDetail getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(QuestionDetail questionDetail) {
		this.questionDetail = questionDetail;
	}

	public List<String> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<String> responseList) {
		this.responseList = responseList;
	}

}
