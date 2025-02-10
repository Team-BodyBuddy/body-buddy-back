package BodyBuddy.demo.domain.trainerCalendar.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;

public record TrainerCalendarSimpleResponse(
	Long calendarId,
	LocalDate date,
	boolean hasClass
) {
	public static TrainerCalendarSimpleResponse from(TrainerCalendar calendar) {
		return new TrainerCalendarSimpleResponse(
			calendar.getId(),
			calendar.getDate(),
			calendar.isHasClass()
		);
	}
}