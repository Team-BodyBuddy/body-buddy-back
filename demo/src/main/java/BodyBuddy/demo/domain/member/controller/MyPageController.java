package BodyBuddy.demo.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.gym.dto.GymResponseDto;
import BodyBuddy.demo.domain.member.dto.MyPageResponseDto;
import BodyBuddy.demo.domain.member.dto.UpdateMemberInfoRequestDto;
import BodyBuddy.demo.domain.member.service.MyPageService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.common.commonEnum.Region;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService myPageService;

	/**
	 * 마이페이지 정보 조회
	 */
	@GetMapping("/{memberId}")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> getMyPage(@PathVariable Long memberId) {
		MyPageResponseDto response = myPageService.getMyPage(memberId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 지역 리스트 제공
	 */
	@GetMapping("/regions")
	public ResponseEntity<ApiResponse<List<String>>> getRegions() {
		List<String> regions = myPageService.getRegions();
		return ResponseEntity.ok(ApiResponse.onSuccess(regions));
	}

	/**
	 * 지역에 따른 GYM 목록 조회
	 */
	@GetMapping("/gyms")
	public ResponseEntity<ApiResponse<List<GymResponseDto>>> getGymsByRegion(@RequestParam Region region) {
		List<GymResponseDto> gyms = myPageService.getGymsByRegion(region);
		return ResponseEntity.ok(ApiResponse.onSuccess(gyms));
	}

	/**
	 * 회원 정보 수정 (지역 및 GYM 선택 포함)
	 */
	@PatchMapping("/{memberId}/update")
	public ResponseEntity<ApiResponse<MyPageResponseDto>> updateMemberInfo(
		@PathVariable Long memberId,
		@RequestBody @Valid UpdateMemberInfoRequestDto requestDto) {
		MyPageResponseDto response = myPageService.updateMemberInfo(memberId, requestDto);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 트레이너 ID 입력 및 인증 요청
	 */
	@PostMapping("/{memberId}/trainer-request")
	public ResponseEntity<ApiResponse<String>> requestTrainerAuthentication(
		@PathVariable Long memberId,
		@RequestParam String trainerId) {
		myPageService.requestTrainerAuthentication(memberId, trainerId);
		return ResponseEntity.ok(ApiResponse.onSuccess("트레이너 인증 요청이 성공적으로 전송되었습니다."));
	}

}
