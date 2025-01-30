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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingAuthenticationController {

	private final MatchingAuthenticationService matchingAuthenticationService;

	/**
	 * 트레이너 인증 요청 리스트 조회
	 */
	@GetMapping("/{trainerId}/requests")
	public ResponseEntity<ApiResponse<List<TrainerRequestDto>>> getRequestList(@PathVariable Long trainerId) {
		List<TrainerRequestDto> response = matchingAuthenticationService.getRequestList(trainerId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 트레이너 인증 요청 수락
	 */
	@PostMapping("/{trainerId}/requests/{requestId}/accept")
	public ResponseEntity<ApiResponse<String>> acceptRequest(
		@PathVariable Long trainerId, @PathVariable Long requestId) {
		matchingAuthenticationService.acceptRequest(trainerId, requestId);
		return ResponseEntity.ok(ApiResponse.onSuccess("요청이 수락되었습니다."));
	}

	/**
	 * 트레이너 인증 요청 거절
	 */
	@PostMapping("/{trainerId}/requests/{requestId}/reject")
	public ResponseEntity<ApiResponse<String>> rejectRequest(
		@PathVariable Long trainerId, @PathVariable Long requestId) {
		matchingAuthenticationService.rejectRequest(trainerId, requestId);
		return ResponseEntity.ok(ApiResponse.onSuccess("요청이 거절되었습니다."));
	}
}
