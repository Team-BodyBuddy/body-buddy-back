package BodyBuddy.demo.domain.calendar.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.EvaluationStatus;
import BodyBuddy.demo.domain.calendar.Calendar;
import BodyBuddy.demo.domain.calendar.dto.CalendarDayInfo;
import BodyBuddy.demo.domain.calendar.dto.CalendarRequest;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/calendars")
@Tag(name = "Calendar", description = "캘린더 API")
public class CalendarController {

	private final CalendarService calendarService;

	public CalendarController(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	/**
	 * 캘린더 생성 API
	 */
	@PostMapping
	public ResponseEntity<Calendar> createOrUpdateCalendar(@RequestBody @Valid CalendarRequest request) {
		Calendar calendar = calendarService.createOrUpdateCalendar(request);
		return ResponseEntity.ok(calendar);
	}

	/**
	 * 특정 회원의 캘린더 조회 API
	 */

	@GetMapping("/{memberId}")
	public ResponseEntity<List<Calendar>> getCalendarByMemberId(@PathVariable @NotNull Long memberId) {
		List<Calendar> calendars = calendarService.getCalendarByMemberId(memberId);
		return ResponseEntity.ok(calendars);
	}

	/**
	 * 캘린더 평가 상태 업데이트 API
	 */
	@PatchMapping("/evaluation")
	public ResponseEntity<Calendar> updateCalendarEvaluation(
		@RequestParam @NotNull Long memberId,
		@RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
		@RequestParam @NotNull EvaluationStatus evaluationStatus) {
		Calendar updatedCalendar = calendarService.updateCalendarEvaluation(memberId, date, evaluationStatus);
		return ResponseEntity.ok(updatedCalendar);
	}

	/**
	 * 특정 회원의 한 달치 점 상태 조회 API
	 */
	@GetMapping("/indicators")
	public ResponseEntity<Map<LocalDate, CalendarDayInfo>> getMonthlyIndicators(
		@RequestParam @NotNull Long memberId,
		@RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		Map<LocalDate, CalendarDayInfo> indicators = calendarService.getMonthlyCalendarData(memberId, startDate, endDate);
		return ResponseEntity.ok(indicators);
	}
}
