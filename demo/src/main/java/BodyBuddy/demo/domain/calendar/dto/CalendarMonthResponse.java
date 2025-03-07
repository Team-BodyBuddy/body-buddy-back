package BodyBuddy.demo.domain.calendar.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;

public record CalendarMonthResponse(Long calendarId, LocalDate date, CalendarDayInfo.IndicatorType indicatorType,
									EvaluationStatus evaluationStatus) {
	public static CalendarMonthResponse from(Calendar calendar){
		return new CalendarMonthResponse(
			calendar.getId(),
			calendar.getDate(),
			calendar.getIndicator(),
			calendar.getEvaluationStatus()
		);
	}

}
