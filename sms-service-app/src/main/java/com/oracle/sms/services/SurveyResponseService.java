package com.oracle.sms.services;

import java.util.List;

import com.oracle.sms.dto.SurveyAnalyticInfo;
import com.oracle.sms.dto.SurveyResponseDetail;
import com.oracle.sms.dto.SurveyResponseRequest;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.exceptions.UserSurveyNotFound;

public interface SurveyResponseService {

	List<SurveyAnalyticInfo> fetchSurveyAnalyticData();

	SurveyResponseDetail fetchUserSurveyResponse(Long surveyId, Long userId) throws SurveyNotFound, UserSurveyNotFound;

	Boolean updateResponse(SurveyResponseRequest responseRequest) throws SurveyNotFound;

	List<SurveyResponseDetail> fetchUserResponses(Long userId);

}
