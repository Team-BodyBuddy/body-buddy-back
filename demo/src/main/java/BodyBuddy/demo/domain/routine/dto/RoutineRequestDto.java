package BodyBuddy.demo.domain.routine.dto;

import java.time.LocalDate;

import BodyBuddy.demo.global.common.commonEnum.RoutineType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoutineRequestDto(
	@NotNull Long memberId,
	@NotNull LocalDate date,
	@NotNull RoutineType routineType,
	@Size(min = 1, max = 100) String name

) {}
