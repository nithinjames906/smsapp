package com.oracle.sms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.sms.constants.ResponseStatus;
import com.oracle.sms.dto.ApiResponse;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.services.SurveyResponseService;

@RestController
public class AnalyticResource {

	@Autowired
	SurveyResponseService surveyResponseService;

	private static final String ANALYTIC_BASE_URL = "/api/v1/analytic";

	@GetMapping(path = ANALYTIC_BASE_URL + "/surveys/stats")
	public ResponseEntity<ApiResponse> findSurveys() throws SurveyNotFound {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				surveyResponseService.fetchSurveyAnalyticData()));
	}
}
