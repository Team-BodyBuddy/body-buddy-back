package BodyBuddy.demo.domain.calendar.controller;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationRequestDto;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
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
public class CalendarController {

	private final CalendarService calendarService;

	/**
	 * 특정 날짜의 캘린더 조회 또는 생성
	 */
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
