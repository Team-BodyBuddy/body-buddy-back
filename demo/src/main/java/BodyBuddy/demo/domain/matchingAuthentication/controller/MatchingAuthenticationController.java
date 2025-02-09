package BodyBuddy.demo.domain.matchingAuthentication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.trainer.dto.TrainerRequestDto;
import BodyBuddy.demo.domain.matchingAuthentication.service.MatchingAuthenticationService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/matching")
@RequiredArgsConstructor
@Tag(name = "트레이너 인증 관리", description = "트레이너 인증 요청 조회 및 승인/거절")
public class MatchingAuthenticationController {

	private final MatchingAuthenticationService matchingAuthenticationService;

	/**
	 * 트레이너 인증 요청 리스트 조회
	 */
	@Operation(summary = "트레이너 인증 요청 목록 조회", description = "트레이너의 인증 요청 목록을 조회합니다.")
	@GetMapping("/{trainerId}/requests")
	public ResponseEntity<ApiResponse<List<TrainerRequestDto>>> getRequestList(@PathVariable Long trainerId) {
		List<TrainerRequestDto> response = matchingAuthenticationService.getRequestList(trainerId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 트레이너 인증 요청 수락
	 */
	@Operation(summary = "트레이너 인증 요청 수락", description = "트레이너 인증 요청을 승인합니다.")
	@PostMapping("/{trainerId}/requests/{requestId}/accept")
	public ResponseEntity<ApiResponse<String>> acceptRequest(
		@PathVariable Long trainerId, @PathVariable Long requestId) {
		matchingAuthenticationService.acceptRequest(trainerId, requestId);
		return ResponseEntity.ok(ApiResponse.onSuccess("요청이 수락되었습니다."));
	}

	/**
	 * 트레이너 인증 요청 거절
	 */
	@Operation(summary = "트레이너 인증 요청 거절", description = "트레이너 인증 요청을 거절합니다.")
	@PostMapping("/{trainerId}/requests/{requestId}/reject")
	public ResponseEntity<ApiResponse<String>> rejectRequest(
		@PathVariable Long trainerId, @PathVariable Long requestId) {
		matchingAuthenticationService.rejectRequest(trainerId, requestId);
		return ResponseEntity.ok(ApiResponse.onSuccess("요청이 거절되었습니다."));
	}
}
