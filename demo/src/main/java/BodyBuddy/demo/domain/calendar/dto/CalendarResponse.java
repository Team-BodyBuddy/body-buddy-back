package BodyBuddy.demo.domain.calendar.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;

import java.time.LocalDate;

public record CalendarResponse(
	Long calendarId,
	LocalDate date,
	CalendarDayInfo.IndicatorType indicator,
	EvaluationStatus evaluationStatus
) {
	public static CalendarResponse from(Calendar calendar) {
		EvaluationStatus status = calendar.getDailyEvaluation() != null
			? calendar.getDailyEvaluation().getEvaluation()
			: EvaluationStatus.NONE;
		return new CalendarResponse(
			calendar.getId(),
			calendar.getDate(),
			calendar.getIndicator(),
			status
		);
	}
}
