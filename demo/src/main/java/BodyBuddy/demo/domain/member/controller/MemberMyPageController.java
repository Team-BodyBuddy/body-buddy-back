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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Tag(name = "마이페이지 관리", description = "회원 마이페이지 조회 및 정보 수정 API")
public class MemberMyPageController {

	private final MemberMyPageService memberMyPageService;

	/**
	 * 마이페이지 정보 조회
	 */
	@GetMapping("/{memberId}")
	@Operation(summary = "마이페이지 정보 조회", description = "특정 회원의 마이페이지 정보를 조회합니다.")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(@PathVariable Long memberId) {
		MyPageResponseDto response = memberMyPageService.getMyPage(memberId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 회원 정보 수정 (지역 및 GYM 선택 포함)
	 */
	@PatchMapping("/{memberId}/update")
	@Operation(summary = "회원 정보 수정", description = "회원의 정보를 수정합니다. (지역 및 GYM 선택 포함)")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> updateMemberInfo(
		@PathVariable Long memberId,
		@RequestBody @Valid UpdateMemberInfoRequestDto requestDto) {
		MyPageResponseDto response = memberMyPageService.updateMemberInfo(memberId, requestDto);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 트레이너 ID 입력 및 인증 요청
	 */
	@Operation(summary = "트레이너 인증 요청", description = "회원이 특정 트레이너 ID를 입력하여 인증을 요청합니다.")
	@PostMapping("/{memberId}/trainer-request")
	public ResponseEntity<ApiResponse<String>> requestTrainerAuthentication(
		@PathVariable Long memberId,
		@RequestParam String trainerId) {
		memberMyPageService.requestTrainerAuthentication(memberId, trainerId);
		return ResponseEntity.ok(ApiResponse.onSuccess("트레이너 인증 요청이 성공적으로 전송되었습니다."));
	}

}
