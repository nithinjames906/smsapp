/**
 * 
 */
package com.oracle.sms.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.oracle.sms.constants.SurveyStatus;
import com.oracle.sms.dto.SurveyDetail;

/**
 * @author nithin james
 * 
 *         Entity holds survey details
 */
@Entity
public class Survey extends SMSDomain {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private Long version;

	@Column(nullable = true)
	private Long parentSurveyId;

	@Column(nullable = true)
	private Long rootSurveyId;

	@Enumerated(EnumType.STRING)
	private SurveyStatus status;

	@Column
	private LocalDateTime createdDate;

	@Column
	private LocalDateTime updatedDate;

	@Column
	private Boolean isActive;

	@Column
	private Long createdBy;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	List<Question> questions;

	public Survey() {
		super();
	}

	public Survey(String title, Long createdBy) {
		super();
		this.title = title;
		this.version = 1L;
		this.isActive = false;
		this.status = SurveyStatus.INPROGRESS;
		this.createdDate = LocalDateTime.now();
		this.createdBy = createdBy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public SurveyStatus getStatus() {
		return status;
	}

	public void setStatus(SurveyStatus status) {
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public SurveyDetail mapResponse() {

		return new SurveyDetail(id, version, parentSurveyId, rootSurveyId, title, status.name(), isActive, createdDate,
				updatedDate, createdBy);
	}

}
