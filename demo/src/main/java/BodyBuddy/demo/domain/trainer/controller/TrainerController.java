package BodyBuddy.demo.domain.trainer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.trainer.dto.TrainerMyPageResponseDto;
import BodyBuddy.demo.domain.trainer.dto.UpdateProfileImageRequestDto;
import BodyBuddy.demo.domain.trainer.service.TrainerService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
public class TrainerController {

	private final TrainerService trainerService;

	/**
	 * 트레이너 마이페이지 정보 조회
	 */
	@GetMapping("/{trainerId}")
	public ResponseEntity<ApiResponse<TrainerMyPageResponseDto>> getTrainerMyPage(@PathVariable Long trainerId) {
		TrainerMyPageResponseDto response = trainerService.getTrainerMyPage(trainerId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 트레이너 프로필 이미지 수정
	 */
	@PatchMapping("/{trainerId}/profile-image")
	public ResponseEntity<ApiResponse<String>> updateProfileImage(
		@PathVariable Long trainerId,
		@RequestBody @Valid UpdateProfileImageRequestDto requestDto) {
		trainerService.updateProfileImage(trainerId, requestDto.profileImageUrl());
		return ResponseEntity.ok(ApiResponse.onSuccess("프로필 이미지가 수정되었습니다."));
	}

}

