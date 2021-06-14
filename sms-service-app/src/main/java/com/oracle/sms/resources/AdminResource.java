package com.oracle.sms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.sms.constants.ResponseStatus;
import com.oracle.sms.dto.AddSurveyRequest;
import com.oracle.sms.dto.ApiResponse;
import com.oracle.sms.dto.SurveyDetail;
import com.oracle.sms.dto.UpdateSurveyRequest;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.services.SurveyService;

@RestController
public class AdminResource {

	private static final String SURVEY_BASE_URL = "/api/v1/surveys";

	@Autowired
	private SurveyService surveyService;

	@GetMapping(path = SURVEY_BASE_URL + "/{surveyId}")
	public ResponseEntity<ApiResponse> findSurvey(@PathVariable(value = "surveyId") Long surveyId) throws SurveyNotFound {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				surveyService.fetch(surveyId)));
	}

	@GetMapping(path = SURVEY_BASE_URL)
	public ResponseEntity<ApiResponse> listAllSurveys() {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				surveyService.fetchAll()));
	}

	@PostMapping(path = SURVEY_BASE_URL)
	public ResponseEntity<ApiResponse> addSurvey(@RequestBody AddSurveyRequest surveyRequest) {

		SurveyDetail surveyDetail = surveyService.add(surveyRequest);
		if (null != surveyDetail.getSurveyId()) {
			return ResponseEntity
					.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Survey Added Successfully", surveyDetail));
		}
		return ResponseEntity.ok(new ApiResponse(ResponseStatus.FAILURE.getStatus(), "Failed to add survey"));
	}

	@PutMapping(path = SURVEY_BASE_URL)
	public ResponseEntity<ApiResponse> updateSurvey(@RequestBody UpdateSurveyRequest surveyRequest)
			throws SurveyNotFound {

		SurveyDetail surveyDetail = surveyService.update(surveyRequest);
		return ResponseEntity
				.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Survey Updated Successfully", surveyDetail));

	}

	@PatchMapping(path = SURVEY_BASE_URL + "/{surveyId}")
	public ResponseEntity<ApiResponse> publishSurvey(@PathVariable(value = "surveyId") Long surveyId,
			@RequestBody UpdateSurveyRequest surveyRequest) throws SurveyNotFound {

		if (surveyService.publish(surveyId))
			return ResponseEntity
					.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Survey Published Successfully"));
		return ResponseEntity.ok(new ApiResponse(ResponseStatus.FAILURE.getStatus(), "Publishing Failed."));

	}

}
