package BodyBuddy.demo.domain.classSchedule.dto;

import BodyBuddy.demo.domain.classSchedule.entity.ClassSchedule;

public record ClassScheduleResponse(
	Long scheduleId,
	String description,
	boolean completed
) {
	public static ClassScheduleResponse from(ClassSchedule classSchedule) {
		return new ClassScheduleResponse(
			classSchedule.getId(),
			classSchedule.getDescription(),
			classSchedule.isCompleted()
		);
	}
}
