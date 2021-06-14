package com.oracle.sms.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.oracle.sms.constants.SurveyStatus;
import com.oracle.sms.domain.Survey;

public interface SurveyRepository extends CrudRepository<Survey, Long> {

	Optional<Survey> findByParentSurveyIdAndStatus(Long surveyId, SurveyStatus inprogress);

}
