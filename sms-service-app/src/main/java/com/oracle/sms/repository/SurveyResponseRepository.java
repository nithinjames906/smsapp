package com.oracle.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.oracle.sms.domain.UserSurvey;

public interface SurveyResponseRepository extends CrudRepository<UserSurvey, Long> {

	Optional<UserSurvey> findBySurveyIdAndUserId(Long surveyId, Long userId);

	List<UserSurvey> findByUserId(Long userId);

}
