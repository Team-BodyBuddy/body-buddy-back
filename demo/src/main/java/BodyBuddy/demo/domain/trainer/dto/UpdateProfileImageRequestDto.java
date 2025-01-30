package BodyBuddy.demo.domain.trainer.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileImageRequestDto(
	@NotBlank String profileImageUrl
) {}