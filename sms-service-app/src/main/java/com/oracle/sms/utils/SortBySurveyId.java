package com.oracle.sms.utils;

import java.util.Comparator;

import com.oracle.sms.dto.SurveyDetail;

public class SortBySurveyId implements Comparator<SurveyDetail> {

	@Override
	public int compare(SurveyDetail o1, SurveyDetail o2) {

		return (int) (o1.getSurveyId() - o2.getSurveyId());
	}
}