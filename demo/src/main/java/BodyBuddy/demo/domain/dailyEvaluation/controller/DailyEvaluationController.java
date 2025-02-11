package BodyBuddy.demo.domain.dailyEvaluation.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationRequestDto;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationResponseDto;
import BodyBuddy.demo.domain.dailyEvaluation.service.DailyEvaluationService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor
@Tag(name = "오늘의 평가 관리", description = "회원의 하루 평가를 관리하는 API")
public class DailyEvaluationController {

	private final DailyEvaluationService dailyEvaluationService;

	/**
	 * 오늘의 평가 상태 설정
	 */
	@Operation(summary = "오늘의 평가 상태 설정", description = "회원이 오늘의 평가 상태를 설정합니다.(NONE,BAD,SOSO,GOOD)")
	@PostMapping("/set")
	public ResponseEntity<ApiResponse<CalendarResponse>> setDailyEvaluation(@RequestBody @Valid DailyEvaluationRequestDto dto) {
		CalendarResponse response = dailyEvaluationService.setDailyEvaluation(dto);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}


	/**
	 * 특정 회원의 특정 날짜의 오늘의 평가 조회
	 */
	@Operation(summary = "오늘의 평가 조회", description = "특정 회원의 특정 날짜의 오늘의 평가를 조회합니다.")
	@GetMapping("/{memberId}/{date}")
	public ResponseEntity<ApiResponse<DailyEvaluationResponseDto>> getDailyEvaluation(
		@PathVariable Long memberId,
		@PathVariable String date
	) {
		LocalDate localDate = LocalDate.parse(date);
		DailyEvaluationResponseDto responseDto = dailyEvaluationService.getDailyEvaluation(memberId, localDate);
		return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
	}
}