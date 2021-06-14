package com.oracle.sms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.sms.constants.ResponseStatus;
import com.oracle.sms.dto.ApiResponse;
import com.oracle.sms.dto.SurveyResponseRequest;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.exceptions.UserSurveyNotFound;
import com.oracle.sms.services.SurveyResponseService;
import com.oracle.sms.services.SurveyService;

@RestController
@RequestMapping(CustomerResource.CUSTOMER_BASE_URL)
public class CustomerResource {

	@Autowired
	SurveyService surveyService;

	@Autowired
	SurveyResponseService surveyResponseService;

	protected static final String CUSTOMER_BASE_URL = "/api/v1/customer";

	@GetMapping(path = "/surveys/{surveyId}")
	public ResponseEntity<ApiResponse> findSurvey(@PathVariable(value = "surveyId") Long surveyId)
			throws SurveyNotFound {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				surveyService.fetch(surveyId)));
	}

	@GetMapping(path = "/surveys")
	public ResponseEntity<ApiResponse> findSurveys() throws SurveyNotFound {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				surveyService.fetchAllPublished()));
	}

	@GetMapping(path = "/surveys/responses/{userId}")
	public ResponseEntity<ApiResponse> fetchUserSurveyResponses(@PathVariable Long userId) throws SurveyNotFound {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				surveyResponseService.fetchUserResponses(userId)));
	}

	@GetMapping(path = "/surveys/response/{surveyId}/{userId}")
	public ResponseEntity<ApiResponse> fetchUserSurveyResponse(@PathVariable Long surveyId, @PathVariable Long userId)
			throws SurveyNotFound, UserSurveyNotFound {

		return ResponseEntity
				.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Survey Response Captured Successfully",
						surveyResponseService.fetchUserSurveyResponse(surveyId, userId)));
	}

	@PostMapping(path = "/surveys/response")
	public ResponseEntity<ApiResponse> captureSurveyResponse(@RequestBody SurveyResponseRequest responseRequest)
			throws SurveyNotFound {

		if (surveyResponseService.updateResponse(responseRequest))
			return ResponseEntity
					.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Survey Response Captured Successfully"));
		return ResponseEntity.ok(new ApiResponse(ResponseStatus.FAILURE.getStatus(), "Response Update Failed."));

	}
}
