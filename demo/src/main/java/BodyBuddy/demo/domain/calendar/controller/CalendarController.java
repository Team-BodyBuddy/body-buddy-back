package BodyBuddy.demo.domain.calendar.controller;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationRequestDto;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

	private final CalendarService calendarService;

	/**
	 * 특정 회원의 달력 조회
	 */
	@GetMapping("/{memberId}")
	public ResponseEntity<ApiResponse<List<CalendarResponse>>> getCalendar(@PathVariable Long memberId) {
		List<CalendarResponse> responses = calendarService.getCalendar(memberId);
		return ResponseEntity.ok(ApiResponse.onSuccess(responses));
	}

	/**
	 * 특정 달의 캘린더 데이터 조회
	 */
	@GetMapping("/{memberId}/{yearMonth}")
	public ResponseEntity<ApiResponse<List<CalendarResponse>>> getCalendarByMonth(
		@PathVariable Long memberId,
		@PathVariable String yearMonth // 형식: YYYY-MM
	) {
		try {
			YearMonth ym = YearMonth.parse(yearMonth);
			List<CalendarResponse> calendarData = calendarService.getCalendarByMonth(memberId, ym);
			return ResponseEntity.ok(ApiResponse.onSuccess(calendarData));
		} catch (DateTimeParseException e) {
			return ResponseEntity.badRequest().body(ApiResponse.onFailure("400", "잘못된 날짜 형식입니다. (YYYY-MM)", null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(ApiResponse.onFailure("400", e.getMessage(), null));
		}
	}

}
