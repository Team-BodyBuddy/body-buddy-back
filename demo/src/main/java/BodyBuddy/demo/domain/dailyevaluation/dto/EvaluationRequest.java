package BodyBuddy.demo.domain.dailyevaluation.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.EvaluationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EvaluationRequest {

	@NotNull
	private Long memberId;
	@NotNull
	private LocalDate date;
	@NotNull
	private EvaluationStatus evaluationStatus;
}