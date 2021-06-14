package com.oracle.sms.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class SurveyDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long surveyId;
	private Long version;
	private Long parentSurveyId;
	private Long rootSurveyId;
	private String title;
	private String status;
	private Boolean isActive;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private Long userId;
	private List<QuestionDetail> questions;

	public SurveyDetail() {
		super();
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getParentSurveyId() {
		return parentSurveyId;
	}

	public void setParentSurveyId(Long parentSurveyId) {
		this.parentSurveyId = parentSurveyId;
	}

	public Long getRootSurveyId() {
		return rootSurveyId;
	}

	public void setRootSurveyId(Long rootSurveyId) {
		this.rootSurveyId = rootSurveyId;
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<QuestionDetail> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDetail> questions) {
		this.questions = questions;
	}

	public SurveyDetail(Long surveyId, Long version, Long parentSurveyId, Long rootSurveyId, String title,
			String status, Boolean isActive, LocalDateTime createdDate, LocalDateTime updatedDate, Long userId) {
		super();
		this.surveyId = surveyId;
		this.userId = userId;
		this.version = version;
		this.parentSurveyId = parentSurveyId;
		this.rootSurveyId = rootSurveyId;
		this.title = title;
		this.status = status;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

}
