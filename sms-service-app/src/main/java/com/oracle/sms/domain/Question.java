package com.oracle.sms.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.oracle.sms.constants.QuestionType;

/**
 * @author nithin james
 * 
 *         Entity holds question details
 */
@Entity
public class Question extends SMSDomain {

	@Column
	private String body;

	@Enumerated(EnumType.STRING)
	private QuestionType type;

	@Column
	private Boolean multiSelect;

	@Column
	private Boolean required;

	@Column
	private String options;

	@Column
	private LocalDateTime createdDate;

	@ManyToOne()
	@JoinColumn(name = "surveyId", referencedColumnName = "id")
	Survey survey;

	public Question() {
		super();
	}

	public Question(Long questionId, String body, QuestionType type, Boolean multiSelect, Boolean required,
			String options, LocalDateTime createdDate, Survey survey) {
		super();
		this.id = questionId;
		this.body = body;
		this.type = type;
		this.multiSelect = multiSelect;
		this.required = required;
		this.options = options;
		this.createdDate = createdDate;
		this.survey = survey;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
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

	public Boolean getMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(Boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

}
