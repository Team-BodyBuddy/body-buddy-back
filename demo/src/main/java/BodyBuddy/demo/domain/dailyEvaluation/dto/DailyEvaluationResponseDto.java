package BodyBuddy.demo.domain.dailyEvaluation.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.dailyEvaluation.entity.DailyEvaluation;
import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;

public record DailyEvaluationResponseDto(
	Long id,
	Long calendarId,
	Long memberId,
	LocalDate date,
	EvaluationStatus evaluation
) {
	public static DailyEvaluationResponseDto from(DailyEvaluation evaluation) {
		return new DailyEvaluationResponseDto(
			evaluation.getId(),
			evaluation.getCalendar().getId(),
			evaluation.getMember().getId(),
			evaluation.getDate(),
			evaluation.getEvaluation()
		);
	}
}