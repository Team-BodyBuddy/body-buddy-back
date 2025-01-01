package BodyBuddy.demo.domain.calendar.dto;

import java.time.LocalDate;


import BodyBuddy.demo.domain.EvaluationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CalendarRequest {

	@NotNull
	private Long memberId;
	@NotNull
	private LocalDate date;
	@NotNull
	private EvaluationStatus evaluationStatus;
}