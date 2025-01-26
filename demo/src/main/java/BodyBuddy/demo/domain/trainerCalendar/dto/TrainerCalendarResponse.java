package BodyBuddy.demo.domain.trainerCalendar.dto;

import java.time.LocalDate;
import java.util.List;

import BodyBuddy.demo.domain.classSchedule.dto.ClassScheduleResponse;
import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;

public record TrainerCalendarResponse(
	Long calendarId,
	LocalDate date,
	List<ClassScheduleResponse> classSchedules,
	String dailyMemo,
	boolean hasClass
) {
	public static TrainerCalendarResponse from(TrainerCalendar trainerCalendar) {
		return new TrainerCalendarResponse(
			trainerCalendar.getId(),
			trainerCalendar.getDate(),
			trainerCalendar.getClassSchedules().stream()
				.map(ClassScheduleResponse::from)
				.toList(),
			trainerCalendar.getDailyMemo() != null ? trainerCalendar.getDailyMemo().getMemo() : null,
			trainerCalendar.isHasClass()
		);
	}
}
