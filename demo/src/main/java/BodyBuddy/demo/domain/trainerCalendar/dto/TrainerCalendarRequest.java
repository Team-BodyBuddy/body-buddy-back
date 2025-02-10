package BodyBuddy.demo.domain.trainerCalendar.dto;

import jakarta.validation.constraints.NotNull;

public record TrainerCalendarRequest(
	@NotNull Long trainerId,
	@NotNull Long memberId,
	@NotNull String date) { }
