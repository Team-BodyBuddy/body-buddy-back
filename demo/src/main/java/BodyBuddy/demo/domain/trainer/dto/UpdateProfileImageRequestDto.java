package BodyBuddy.demo.domain.trainer.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileImageRequestDto(
	@NotBlank(message = "프로필 이미지 URL은 필수 입력 항목입니다.")
	String profileImageUrl
) {}