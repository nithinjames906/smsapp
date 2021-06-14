package com.oracle.sms.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oracle.sms.constants.QuestionType;
import com.oracle.sms.constants.SurveyStatus;
import com.oracle.sms.domain.Question;
import com.oracle.sms.domain.Survey;
import com.oracle.sms.domain.UserSurvey;
import com.oracle.sms.domain.UserSurveyResponse;
import com.oracle.sms.dto.SurveyAnalyticInfo;
import com.oracle.sms.dto.SurveyDetail;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.repository.SurveyResponseRepository;
import com.oracle.sms.services.SurveyResponseService;
import com.oracle.sms.services.SurveyService;
import com.oracle.sms.services.impl.SurveyResponseServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.oracle.sms.*")
public class SurveyResponseServiceTest {

	@Mock
	SurveyResponseRepository surveyResponseRepo;

	@Mock
	SurveyService surveyService;

	@InjectMocks
	SurveyResponseService surveyResponseService = new SurveyResponseServiceImpl();

	private SurveyDetail getSurveyDetail(String title, Long surveyId, Long rootSurveyId, SurveyStatus status,
			Boolean isActive) {
		Survey survey = new Survey(title, 1L);
		survey.setStatus(status);
		survey.setId(surveyId);
		survey.setRootSurveyId(rootSurveyId);
		survey.setIsActive(isActive);
		List<Question> questions = Arrays.asList(getFreeTextQuestion(1L, survey),
				getChoiceSingleSelectQuestion(2L, survey), getChoiceMultiSelectQuestion(3L, survey));
		survey.setQuestions(questions);
		return survey.mapResponse();
	}

	private Question getFreeTextQuestion(Long questionId, Survey survey) {
		return new Question(questionId, "FreeText Question", QuestionType.FREETEXT, false, false, "", LocalDateTime.now(),
				survey);
	}

	private Question getChoiceSingleSelectQuestion(Long questionId, Survey survey) {
		return new Question(questionId, "Single Choice Question", QuestionType.CHOICE, false, false, "Yes,No",
				LocalDateTime.now(), survey);
	}

	private Question getChoiceMultiSelectQuestion(Long questionId, Survey survey) {
		return new Question(questionId, "Multi Choice Question", QuestionType.CHOICE, true, false, "Red,Orange,Green,Yellow",
				LocalDateTime.now(), survey);
	}

	private UserSurvey getUserSurvey(Long surveyId, Long userId, SurveyStatus status) {
		UserSurvey userSurvey = new UserSurvey(surveyId, userId);
		List<UserSurveyResponse> responses = Arrays.asList(getFreeTextResponse(1L, userSurvey),
				getChoiceSingleSelectResponse(2L, userSurvey), getChoiceMultiSelectResponse(3L, userSurvey));
		userSurvey.setResponses(responses);
		userSurvey.setUserId(userId);
		userSurvey.setStatus(status);
		return userSurvey;
	}

	private UserSurveyResponse getFreeTextResponse(Long questionId, UserSurvey userSurvey) {
		return new UserSurveyResponse(questionId, "Hai", userSurvey);
	}

	private UserSurveyResponse getChoiceSingleSelectResponse(Long questionId, UserSurvey userSurvey) {
		return new UserSurveyResponse(questionId, "Yes", userSurvey);
	}

	private UserSurveyResponse getChoiceMultiSelectResponse(Long questionId, UserSurvey userSurvey) {
		return new UserSurveyResponse(questionId, "Red,Orange", userSurvey);
	}

	@Test
	public void testFetchUserResponses() throws SurveyNotFound {
		Mockito.when(surveyService.fetchAll())
				.thenReturn(Arrays.asList(getSurveyDetail("Test Survey 1", 1L, null, SurveyStatus.PUBLISHED, true),
						getSurveyDetail("Test Survey 2", 2L, null, SurveyStatus.PUBLISHED, true),
						getSurveyDetail("Test Survey 3", 3L, null, SurveyStatus.PUBLISHED, true),
						getSurveyDetail("Test Survey 4", 4L, null, SurveyStatus.PUBLISHED, true)));
		Mockito.when(surveyResponseRepo.findAll())
				.thenReturn(Arrays.asList(getUserSurvey(1L, 1L, SurveyStatus.INPROGRESS),
						getUserSurvey(2L, 2L, SurveyStatus.NEW), getUserSurvey(3L, 1L, SurveyStatus.COMPLETED),
						getUserSurvey(3L, 2L, SurveyStatus.COMPLETED)));
		List<SurveyAnalyticInfo> analyticResponse = surveyResponseService.fetchSurveyAnalyticData();
		assertEquals(4, analyticResponse.size());
		Optional<SurveyAnalyticInfo> surveyOpt = analyticResponse.stream().filter(obj -> obj.getSurveyId().equals(3L))
				.findFirst();
		assertEquals(true, surveyOpt.isPresent());
		assertEquals(2, surveyOpt.get().getCompletedCount());
	}

}
