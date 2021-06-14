package com.oracle.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author nithin james
 * 
 *         Entity holds user survey response
 */
@Entity
@Table(name = "user_survey_response")
public class UserSurveyResponse extends SMSDomain {

	@Column
	private Long questionId;

	@Column
	private String response;

	@ManyToOne()
	@JoinColumn(name = "surveyId", referencedColumnName = "surveyId")
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	UserSurvey userSurvey;

	public UserSurveyResponse() {
		super();
	}

	public UserSurveyResponse(Long questionId, String response, UserSurvey userSurvey) {
		super();
		this.questionId = questionId;
		this.response = response;
		this.userSurvey = userSurvey;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public UserSurvey getUserSurvey() {
		return userSurvey;
	}

	public void setUserSurvey(UserSurvey userSurvey) {
		this.userSurvey = userSurvey;
	}

}
