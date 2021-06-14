package com.oracle.sms.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.oracle.sms.constants.QuestionType;

public class QuestionDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long questionId;
	String body;
	QuestionType type;
	Boolean multiSelect;
	Boolean required;
	LocalDateTime createdDate;
	List<String> options;

	public QuestionDetail() {
		super();
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public QuestionDetail(Long questionId, String body, QuestionType type, Boolean multiSelect, Boolean required,
			LocalDateTime createdDate, List<String> options) {
		super();
		this.questionId = questionId;
		this.body = body;
		this.type = type;
		this.multiSelect = multiSelect;
		this.required = required;
		this.createdDate = createdDate;
		this.options = options;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Boolean getMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(Boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

}
