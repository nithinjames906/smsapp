package com.oracle.sms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.oracle.sms.constants.SurveyStatus;

/**
 * @author nithin james
 * 
 *         Entity holds user survey details
 */
@Entity
@IdClass(UserSurveyId.class)
@Table(name = "user_survey")
public class UserSurvey {

	@Id
	private Long surveyId;

	@Id
	private Long userId;

	@Enumerated(EnumType.STRING)
	private SurveyStatus status;

	@OneToMany(mappedBy = "userSurvey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	List<UserSurveyResponse> responses;

	public UserSurvey() {
		super();
	}

	public UserSurvey(Long surveyId, Long userId) {
		super();
		this.surveyId = surveyId;
		this.userId = userId;
		this.status = SurveyStatus.INPROGRESS;
		this.responses = new ArrayList<UserSurveyResponse>();
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public SurveyStatus getStatus() {
		return status;
	}

	public void setStatus(SurveyStatus status) {
		this.status = status;
	}

	public List<UserSurveyResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<UserSurveyResponse> responses) {
		this.responses = responses;
	}

}
