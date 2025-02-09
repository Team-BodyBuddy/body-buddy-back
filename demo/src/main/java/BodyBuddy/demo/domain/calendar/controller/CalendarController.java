package BodyBuddy.demo.domain.calendar.controller;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationRequestDto;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Tag(name = "회원 캘린더 관리", description = "회원의 루틴 및 수업을 관리하는 캘린더 API")
public class CalendarController {

	private final CalendarService calendarService;

	/**
	 * 특정 날짜의 캘린더 조회 또는 생성
	 */
	@Operation(summary = "특정 날짜의 캘린더 조회", description = "특정 날짜의 회원 캘린더를 조회하거나 생성합니다.")
	@GetMapping("/{memberId}/date")
	public ResponseEntity<ApiResponse<CalendarResponse>> getCalendarByDate(
		@PathVariable Long memberId,
		@RequestParam String date // 형식: YYYY-MM-DD
	) {
		try {
			LocalDate parsedDate = LocalDate.parse(date);
			Calendar calendar = calendarService.getOrCreateCalendarByDate(memberId, parsedDate);
			return ResponseEntity.ok(ApiResponse.onSuccess(CalendarResponse.from(calendar)));
		} catch (DateTimeParseException e) {
			return ResponseEntity.badRequest().body(ApiResponse.onFailure("400", "잘못된 날짜 형식입니다. (YYYY-MM-DD)", null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(ApiResponse.onFailure("400", e.getMessage(), null));
		}
	}


}
