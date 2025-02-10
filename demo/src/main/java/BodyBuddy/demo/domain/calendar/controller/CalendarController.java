package BodyBuddy.demo.domain.calendar.controller;

import BodyBuddy.demo.domain.calendar.dto.CalendarMonthResponse;
import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationRequestDto;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
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

	@Operation(summary = "특정 달의 캘린더 조회",
		description = "요청된 달(yyyy-MM)에 해당하는 회원 캘린더 목록을 조회합니다. "
			+ "month 파라미터가 없으면 기본으로 현재 달을 사용합니다.")
	@GetMapping("/{memberId}/month")
	public ResponseEntity<ApiResponse<List<CalendarMonthResponse>>> getCalendarsByMonth(
		@PathVariable  Long memberId,
		@RequestParam(required = false)  String month) {
		try {
			List<CalendarMonthResponse> responses = calendarService.getCalendarsByMonth(memberId, month);
			return ResponseEntity.ok(ApiResponse.onSuccess(responses));
		} catch (DateTimeParseException e) {
			return ResponseEntity.badRequest().body(
				ApiResponse.onFailure("400", "잘못된 달 형식입니다. (yyyy-MM)", null));
		} catch (BodyBuddyException e) {
			return ResponseEntity.badRequest().body(
				ApiResponse.onFailure("400", e.getMessage(), null));
		}
	}

}
