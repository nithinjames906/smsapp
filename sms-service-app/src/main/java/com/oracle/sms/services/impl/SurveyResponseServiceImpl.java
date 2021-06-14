package com.oracle.sms.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.sms.constants.SurveyStatus;
import com.oracle.sms.domain.UserSurvey;
import com.oracle.sms.domain.UserSurveyResponse;
import com.oracle.sms.dto.QuestionDetail;
import com.oracle.sms.dto.QuestionResponseDetail;
import com.oracle.sms.dto.SurveyAnalyticInfo;
import com.oracle.sms.dto.SurveyDetail;
import com.oracle.sms.dto.SurveyResponseDetail;
import com.oracle.sms.dto.SurveyResponseRequest;
import com.oracle.sms.exceptions.SurveyNotFound;
import com.oracle.sms.exceptions.UserSurveyNotFound;
import com.oracle.sms.repository.SurveyResponseRepository;
import com.oracle.sms.services.SurveyResponseService;
import com.oracle.sms.services.SurveyService;

@Component
public class SurveyResponseServiceImpl implements SurveyResponseService {

	Logger logger = LoggerFactory.getLogger(SurveyResponseServiceImpl.class);

	@Autowired
	SurveyService surveyService;

	@Autowired
	SurveyResponseRepository surveyResponseRepo;

	@Override
	public List<SurveyAnalyticInfo> fetchSurveyAnalyticData() {
		logger.debug("fetchSurveyAnalyticData()");
		Iterable<UserSurvey> userSurveys = surveyResponseRepo.findAll();
		List<SurveyDetail> surveys = surveyService.fetchAll();
		List<SurveyAnalyticInfo> surveyAnalytics = new ArrayList<SurveyAnalyticInfo>();
		surveys.stream().filter(obj -> obj.getIsActive()).forEach(survey -> {
			SurveyAnalyticInfo analyticInfo = new SurveyAnalyticInfo();
			analyticInfo.setTitle(survey.getTitle());
			analyticInfo.setSurveyId(survey.getSurveyId());
			analyticInfo.incrementCompletedCount(0);
			surveyAnalytics.add(analyticInfo);
		});
		userSurveys.forEach(userSurvey -> {
			Optional<SurveyDetail> surveyDetailOpt = surveys.stream()
					.filter(obj -> obj.getSurveyId().equals(userSurvey.getSurveyId())).findFirst();

			if (surveyDetailOpt.isPresent()) {
				SurveyDetail surveyDetail = surveyDetailOpt.get();
				Optional<SurveyAnalyticInfo> surveyAnalyticOpt = surveyAnalytics.stream()
						.filter(obj -> surveyDetail.getSurveyId().equals(obj.getSurveyId())).findFirst();
				if (surveyAnalyticOpt.isPresent())
					updateCount(surveyAnalytics, userSurvey, surveyAnalyticOpt.get());
				else {
					surveyAnalyticOpt = surveyAnalytics.stream()
							.filter(obj -> surveyDetail.getRootSurveyId().equals(obj.getSurveyId())).findFirst();
					if (surveyAnalyticOpt.isPresent())
						updateCount(surveyAnalytics, userSurvey, surveyAnalyticOpt.get());
				}
			}
		});
		return surveyAnalytics;
	}

	@Override
	public SurveyResponseDetail fetchUserSurveyResponse(Long surveyId, Long userId)
			throws SurveyNotFound, UserSurveyNotFound {
		logger.debug("fetchUserSurveyResponse(surveyId->" + surveyId + ", userId->" + userId + ")");
		SurveyDetail surveyDetail = surveyService.fetch(surveyId);
		Optional<UserSurvey> userSurveyOpt = surveyResponseRepo.findBySurveyIdAndUserId(surveyId, userId);
		SurveyResponseDetail surveyResponseDetail = new SurveyResponseDetail(surveyId, userId, surveyDetail.getTitle());
		if (!userSurveyOpt.isPresent() || null == userId || userId <= 0) {
			surveyResponseDetail.setStatus(SurveyStatus.NEW.name());
			return surveyResponseDetail;
		} else {
			surveyResponseDetail.setStatus(userSurveyOpt.get().getStatus().name());
		}
		return mapUserSurveyDetail(surveyDetail, userSurveyOpt.get(), surveyResponseDetail);
	}

	@Override
	public Boolean updateResponse(SurveyResponseRequest responseRequest) throws SurveyNotFound {
		logger.debug("updateResponse(surveyId->" + responseRequest.getSurveyId() + ", userId->"
				+ responseRequest.getUserId() + ")");
		surveyService.fetch(responseRequest.getSurveyId());
		Optional<UserSurvey> existingUserSurvey = surveyResponseRepo
				.findBySurveyIdAndUserId(responseRequest.getSurveyId(), responseRequest.getUserId());
		if (existingUserSurvey.isPresent()) {
			UserSurvey userSurveyOld = existingUserSurvey.get();
			List<UserSurveyResponse> responses = (null != userSurveyOld.getResponses()) ? userSurveyOld.getResponses()
					: new ArrayList<UserSurveyResponse>();
			responses.clear();
			mapUserSurveyResponses(responseRequest, userSurveyOld, responses);
			userSurveyOld.setResponses(responses);
			surveyResponseRepo.save(userSurveyOld);
			logger.info("Survey response updated. surveyId->" + userSurveyOld.getSurveyId() + ", userId->"
					+ userSurveyOld.getUserId());
		} else {
			UserSurvey userSurvey = new UserSurvey(responseRequest.getSurveyId(), responseRequest.getUserId());
			mapUserSurveyResponses(responseRequest, userSurvey, userSurvey.getResponses());
			surveyResponseRepo.save(userSurvey);
			logger.info("Survey response created. surveyId->" + userSurvey.getSurveyId() + ", userId->"
					+ userSurvey.getUserId());
		}
		return true;
	}

	@Override
	public List<SurveyResponseDetail> fetchUserResponses(Long userId) {
		logger.debug("fetchUserResponses(userId->" + userId + ")");
		List<SurveyDetail> publishedSurveys = surveyService.fetchAllPublished();
		List<SurveyResponseDetail> details = new ArrayList<SurveyResponseDetail>();
		List<UserSurvey> userSurveys = surveyResponseRepo.findByUserId(userId);
		userSurveys.forEach(userSurvey -> {
			logger.debug("userSurvey->" + userSurvey.getSurveyId());
			SurveyDetail surveyDetail;
			try {
				surveyDetail = surveyService.fetch(userSurvey.getSurveyId());
				SurveyResponseDetail surveyResponseDetail = new SurveyResponseDetail(userSurvey.getSurveyId(), userId,
						surveyDetail.getTitle(), userSurvey.getStatus().name());
				details.add(mapUserSurveyDetail(surveyDetail, userSurvey, surveyResponseDetail));
			} catch (SurveyNotFound e) {
				logger.error("Survey not found. id->" + userSurvey.getSurveyId());
				e.printStackTrace();
			}
			Optional<SurveyDetail> publishedOpt = publishedSurveys.stream().filter(obj -> obj.getSurveyId()
					.equals(userSurvey.getSurveyId())
					|| (null != obj.getRootSurveyId() && obj.getRootSurveyId().equals(userSurvey.getSurveyId())))
					.findFirst();
			if (publishedOpt.isPresent()) {
				int index = publishedSurveys.indexOf(publishedOpt.get());
				publishedSurveys.remove(index);
			}
		});
		publishedSurveys.forEach(published -> {
			SurveyResponseDetail surveyResponseDetail = new SurveyResponseDetail(published.getSurveyId(), userId,
					published.getTitle(), SurveyStatus.NEW.name());
			details.add(surveyResponseDetail);
		});
		return details;
	}

	private SurveyResponseDetail mapUserSurveyDetail(SurveyDetail surveyDetail, UserSurvey userSurveyOpt,
			SurveyResponseDetail surveyResponseDetail) {
		List<QuestionDetail> questions = surveyDetail.getQuestions();
		userSurveyOpt.getResponses().forEach(qtnResp -> {

			List<String> respList = Collections.emptyList();
			Optional<QuestionDetail> question = questions.stream()
					.filter(q -> qtnResp.getQuestionId() == q.getQuestionId()).findFirst();
			try {
				respList = Arrays.asList(qtnResp.getResponse().split(","));
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to map survey response options.");
			}
			surveyResponseDetail.getResponseDetails().add(new QuestionResponseDetail(question.get(), respList));
		});
		return surveyResponseDetail;
	}

	private List<UserSurveyResponse> mapUserSurveyResponses(SurveyResponseRequest responseRequest,
			UserSurvey userSurvey, List<UserSurveyResponse> responses) {
		List<Long> pendingQuestions = new ArrayList<Long>();
		responseRequest.getSurveyResponses().forEach(qtn -> {
			String responseDelimited = String.join(",", qtn.getResponseList());
			if (null == responseDelimited || responseDelimited.isEmpty())
				pendingQuestions.add(qtn.getQuestionId());
			responses.add(new UserSurveyResponse(qtn.getQuestionId(), responseDelimited, userSurvey));
		});
		if (pendingQuestions.isEmpty()) {
			userSurvey.setStatus(SurveyStatus.COMPLETED);
		}
		return responses;
	}

	private void updateCount(List<SurveyAnalyticInfo> surveyAnalytics, UserSurvey userSurvey,
			SurveyAnalyticInfo surveyAnalyticObj) {
		int index = surveyAnalytics.indexOf(surveyAnalyticObj);
		if (SurveyStatus.COMPLETED.equals(userSurvey.getStatus())) {
			surveyAnalyticObj.incrementCompletedCount(1);
			surveyAnalytics.set(index, surveyAnalyticObj);
		} else {
			surveyAnalyticObj.incrementAttendedCount(1);
			surveyAnalytics.set(index, surveyAnalyticObj);
		}
	}

}
