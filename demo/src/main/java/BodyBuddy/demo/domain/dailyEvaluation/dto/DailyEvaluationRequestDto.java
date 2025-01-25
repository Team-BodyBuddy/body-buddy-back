package BodyBuddy.demo.domain.dailyEvaluation.dto;

import java.time.LocalDate;

import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;
import BodyBuddy.demo.global.common.commonEnum.RoutineType;
import jakarta.validation.constraints.NotNull;

public record DailyEvaluationRequestDto(
	@NotNull Long memberId,
	@NotNull LocalDate date,
	@NotNull EvaluationStatus status

) {}