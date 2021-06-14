package com.oracle.sms.services;

import java.util.List;

import com.oracle.sms.dto.AddSurveyRequest;
import com.oracle.sms.dto.SurveyDetail;
import com.oracle.sms.dto.UpdateSurveyRequest;
import com.oracle.sms.exceptions.SurveyNotFound;

public interface SurveyService {

	SurveyDetail fetch(Long surveyId) throws SurveyNotFound;

	List<SurveyDetail> fetchAll();

	List<SurveyDetail> fetchAllPublished();

	SurveyDetail add(AddSurveyRequest request);

	SurveyDetail update(UpdateSurveyRequest request) throws SurveyNotFound;

	Boolean publish(Long surveyId) throws SurveyNotFound;
}
