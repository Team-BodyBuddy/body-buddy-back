package BodyBuddy.demo.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.member.dto.MyPageResponseDto;
import BodyBuddy.demo.domain.member.dto.UpdateMemberInfoRequestDto;
import BodyBuddy.demo.domain.member.service.MemberMyPageService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MemberMyPageController {

	private final MemberMyPageService memberMyPageService;

	/**
	 * 마이페이지 정보 조회
	 */
	@GetMapping("/{memberId}")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(@PathVariable Long memberId) {
		MyPageResponseDto response = memberMyPageService.getMyPage(memberId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 회원 정보 수정 (지역 및 GYM 선택 포함)
	 */
	@PatchMapping("/{memberId}/update")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> updateMemberInfo(
		@PathVariable Long memberId,
		@RequestBody @Valid UpdateMemberInfoRequestDto requestDto) {
		MyPageResponseDto response = memberMyPageService.updateMemberInfo(memberId, requestDto);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 트레이너 ID 입력 및 인증 요청
	 */
	@PostMapping("/{memberId}/trainer-request")
	public ResponseEntity<ApiResponse<String>> requestTrainerAuthentication(
		@PathVariable Long memberId,
		@RequestParam String trainerId) {
		memberMyPageService.requestTrainerAuthentication(memberId, trainerId);
		return ResponseEntity.ok(ApiResponse.onSuccess("트레이너 인증 요청이 성공적으로 전송되었습니다."));
	}

}
