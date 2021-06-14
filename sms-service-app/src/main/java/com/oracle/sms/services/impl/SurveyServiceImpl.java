package com.oracle.sms.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.sms.constants.SurveyStatus;
import com.oracle.sms.domain.Question;
import com.oracle.sms.domain.Survey;
import com.oracle.sms.dto.AddSurveyRequest;
import com.oracle.sms.dto.QuestionDetail;
import com.oracle.sms.dto.SurveyDetail;
import com.oracle.sms.dto.UpdateSurveyRequest;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.repository.SurveyRepository;
import com.oracle.sms.services.SurveyService;
import com.oracle.sms.utils.SortBySurveyId;

@Component
public class SurveyServiceImpl implements SurveyService {

	Logger logger = LoggerFactory.getLogger(SurveyServiceImpl.class);

	@Autowired
	SurveyRepository surveyRepo;

	@Override
	public SurveyDetail fetch(Long surveyId) throws SurveyNotFound {
		logger.debug("fetchAll(" + surveyId + ")");
		Optional<Survey> surveyOpt = surveyRepo.findById(surveyId);
		if (!surveyOpt.isPresent()) {
			throw new SurveyNotFound(surveyId);
		}
		return mapSurveyDetailResponse(surveyOpt.get());
	}

	@Override
	public List<SurveyDetail> fetchAll() {
		logger.debug("fetchAll()");
		List<SurveyDetail> surveys = new ArrayList<SurveyDetail>();
		surveyRepo.findAll().forEach(survey -> {
			surveys.add(survey.mapResponse());
		});
		Comparator<SurveyDetail> comp = Collections.reverseOrder(new SortBySurveyId());
		Collections.sort(surveys, comp);
		return surveys;
	}

	@Override
	public SurveyDetail add(AddSurveyRequest request) {
		logger.debug("add{AddSurveyRequest}");
		Survey survey = new Survey(request.getTitle(), request.getUserId());
		mapQuestions(survey, request.getQuestions());
		survey = surveyRepo.save(survey);
		logger.info("Survey created. id->" + survey.getId());
		return survey.mapResponse();
	}

	@Override
	public SurveyDetail update(UpdateSurveyRequest request) throws SurveyNotFound {
		logger.debug("update{UpdateSurveyRequest}");
		Optional<Survey> surveyOpt = surveyRepo.findById(request.getSurveyId());
		if (!surveyOpt.isPresent()) {
			throw new SurveyNotFound(request.getSurveyId());
		}
		Survey survey = this.updateSurvey(surveyOpt.get(), request);
		survey = surveyRepo.save(survey);
		logger.info("Survey updated. id->" + survey.getId());
		return mapSurveyDetailResponse(survey);
	}

	@Override
	public Boolean publish(Long surveyId) throws SurveyNotFound {
		logger.debug("publish(surveyId->" + surveyId + ")");
		Optional<Survey> surveyOpt = surveyRepo.findById(surveyId);
		if (!surveyOpt.isPresent()) {
			throw new SurveyNotFound(surveyId);
		}
		if (null != surveyOpt.get().getParentSurveyId()) {
			Optional<Survey> parentSurveyOpt = surveyRepo.findById(surveyOpt.get().getParentSurveyId());
			if (!parentSurveyOpt.isPresent()) {
				throw new SurveyNotFound(surveyId);
			}
			parentSurveyOpt.get().setIsActive(false);
			surveyRepo.save(parentSurveyOpt.get());
		}
		surveyOpt.get().setIsActive(true);
		surveyOpt.get().setStatus(SurveyStatus.PUBLISHED);
		surveyRepo.save(surveyOpt.get());
		logger.info("Survey published. id->" + surveyOpt.get().getId());
		return true;
	}

	@Override
	public List<SurveyDetail> fetchAllPublished() {
		logger.debug("fetchAllPublished()");
		List<SurveyDetail> surveys = new ArrayList<SurveyDetail>();
		surveyRepo.findAll().forEach(survey -> {
			if (survey.getIsActive())
				surveys.add(mapSurveyDetailResponse(survey));
		});

		Comparator<SurveyDetail> comp = Collections.reverseOrder(new SortBySurveyId());
		Collections.sort(surveys, comp);
		
		return surveys.stream().filter(survey -> SurveyStatus.PUBLISHED.name().equalsIgnoreCase(survey.getStatus()))
				.collect(Collectors.toList());
	}

	private Survey mapQuestions(Survey survey, List<QuestionDetail> questions) {
		logger.debug("mapQuestions invoked");
		List<Question> qtnList = (null != survey.getQuestions()) ? survey.getQuestions() : new ArrayList<Question>();
		qtnList.clear();
		questions.stream().forEach(question -> {
			String optionsDelimited = String.join(",", question.getOptions());
			qtnList.add(new Question(null, question.getBody(), question.getType(), question.getMultiSelect(), question.getRequired(),
					optionsDelimited, question.getCreatedDate(), survey));
		});
		survey.setQuestions(qtnList);
		return survey;
	}

	public QuestionDetail mapQuestionResponse(Question question) {
		logger.debug("mapQuestionResponse invoked");
		List<String> optionsList = Collections.emptyList();
		try {
			optionsList = Arrays.asList(question.getOptions().split(","));
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to map question options.");
		}
		return new QuestionDetail(question.getId(), question.getBody(), question.getType(), question.getMultiSelect(), question.getRequired(),
				question.getCreatedDate(), optionsList);
	}

	private SurveyDetail mapSurveyDetailResponse(Survey survey) {
		logger.debug("mapSurveyDetailResponse invoked");
		SurveyDetail detail = survey.mapResponse();
		if (!survey.getQuestions().isEmpty())
			detail.setQuestions(survey.getQuestions().stream().map(qtn -> {
				return mapQuestionResponse(qtn);
			}).collect(Collectors.toList()));
		return detail;
	}

	private Survey updateSurvey(Survey survey, UpdateSurveyRequest request) {
		logger.debug("updateSurvey invoked");
		Optional<Survey> surveyInprogress = surveyRepo.findByParentSurveyIdAndStatus(request.getSurveyId(),
				SurveyStatus.INPROGRESS);
		if (SurveyStatus.INPROGRESS.equals(survey.getStatus())) {
			survey.setTitle(request.getTitle());
			survey.setUpdatedDate(LocalDateTime.now());
			mapQuestions(survey, request.getQuestions());
			return survey;
		} else if (surveyInprogress.isPresent() && SurveyStatus.PUBLISHED.equals(survey.getStatus())) {
			surveyInprogress.get().setTitle(request.getTitle());
			surveyInprogress.get().setUpdatedDate(LocalDateTime.now());
			mapQuestions(surveyInprogress.get(), request.getQuestions());
			return surveyInprogress.get();
		} else {
			Survey newSurvey = new Survey(request.getTitle(), request.getUserId());
			newSurvey.setQuestions(new ArrayList<Question>());
			request.getQuestions().stream().forEach(question -> {
				String optionsDelimited = String.join(",", question.getOptions());
				newSurvey.getQuestions().add(new Question(null, question.getBody(), question.getType(),
						question.getMultiSelect(), question.getRequired(), optionsDelimited, question.getCreatedDate(), newSurvey));
			});
			newSurvey.setCreatedDate(survey.getCreatedDate());
			newSurvey.setUpdatedDate(LocalDateTime.now());
			newSurvey.setVersion(survey.getVersion() + 1);
			newSurvey.setParentSurveyId(survey.getId());
			newSurvey.setRootSurveyId(survey.getParentSurveyId() != null ? survey.getParentSurveyId() : survey.getId());
			return newSurvey;
		}
	}

}
