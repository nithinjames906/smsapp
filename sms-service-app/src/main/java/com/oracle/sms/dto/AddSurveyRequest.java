package com.oracle.sms.dto;

import java.io.Serializable;
import java.util.List;

public class AddSurveyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String title;
	Long userId;
	List<QuestionDetail> questions;

	public AddSurveyRequest() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<QuestionDetail> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDetail> questions) {
		this.questions = questions;
	}

}
