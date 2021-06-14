package com.oracle.sms.dto;

public class SurveyAnalyticInfo {

	Long surveyId;
	String title;
	int attendedCount;
	int completedCount;

	public SurveyAnalyticInfo() {
		super();
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAttendedCount() {
		return attendedCount;
	}

	public void setAttendedCount(int attendedCount) {
		this.attendedCount = attendedCount;
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	public void incrementCompletedCount(int completedCount) {
		this.completedCount += completedCount;
	}

	public void incrementAttendedCount(int attendedCount) {
		this.attendedCount += attendedCount;
	}

}
