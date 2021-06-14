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
import com.oracle.sms.domain.Question;
import com.oracle.sms.domain.Survey;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.repository.SurveyRepository;
import com.oracle.sms.services.SurveyService;
import com.oracle.sms.services.impl.SurveyServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.oracle.sms.*")
public class SurveyServiceTest {

	@Mock
	SurveyRepository surveyRepo;

	@InjectMocks
	SurveyService surveyService = new SurveyServiceImpl();

	private Survey getSurvey(String title) {
		Survey survey = new Survey(title, 1L);
		List<Question> questions = Arrays.asList(getFreeTextQuestion(1L, survey),
				getChoiceSingleSelectQuestion(2L, survey), getChoiceMultiSelectQuestion(3L, survey));
		survey.setQuestions(questions);
		return survey;
	}

	private Question getFreeTextQuestion(Long questionId, Survey survey) {
		return new Question(questionId, "FreeText Question", QuestionType.FREETEXT, false, false, "", LocalDateTime.now(),
				survey);
	}

	private Question getChoiceSingleSelectQuestion(Long questionId, Survey survey) {
		return new Question(questionId, "Single Choice Question", QuestionType.CHOICE, false, false, "", LocalDateTime.now(),
				survey);
	}

	private Question getChoiceMultiSelectQuestion(Long questionId, Survey survey) {
		return new Question(questionId, "Multi Choice Question", QuestionType.CHOICE, true, false, "", LocalDateTime.now(),
				survey);
	}

	@Test
	public void testSurveyFound() throws SurveyNotFound {
		Mockito.when(surveyRepo.findById(1L)).thenReturn(Optional.of(getSurvey("Test Survey 1")));
		assertEquals("Test Survey 1", surveyService.fetch(1L).getTitle());
	}

	@Test(expected = SurveyNotFound.class)
	public void testSurveyNotFound() throws SurveyNotFound {
		Mockito.when(surveyRepo.findById(1L)).thenReturn(Optional.empty());
		assertEquals("Test Survey 1", surveyService.fetch(1L).getTitle());
	}

}
