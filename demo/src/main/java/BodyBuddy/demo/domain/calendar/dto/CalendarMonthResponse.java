package BodyBuddy.demo.domain.calendar.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.calendar.entity.Calendar;

public record CalendarMonthResponse(Long calendarId, LocalDate date, CalendarDayInfo.IndicatorType indicatorType) {
	public static CalendarMonthResponse from(Calendar calendar){
		return new CalendarMonthResponse(
			calendar.getId(),
			calendar.getDate(),
			calendar.getIndicator()
		);
	}

}
