package BodyBuddy.demo.domain.routine.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.routine.entity.Routine;
import BodyBuddy.demo.global.common.commonEnum.RoutineType;

public record RoutineResponseDto(
	Long id,
	Long memberId,
	LocalDate date,
	RoutineType type,
	String name,
	Boolean completed
) {
	public static RoutineResponseDto from(Routine routine) {
		return new RoutineResponseDto(
			routine.getId(),
			routine.getMember().getId(),
			routine.getDate(),
			routine.getType(),
			routine.getName(),
			routine.getCompleted()
		);
	}
}