package BodyBuddy.demo.domain.calendar.controller;

import BodyBuddy.demo.domain.calendar.CalendarMapper;
import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
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
    @Operation(
        summary = "캘린더 생성 및 업데이트",
        description = "캘린더를 생성하거나 이미 존재하는 경우 업데이트합니다."
    )
    public ResponseEntity<ApiResponse<CalendarResponse>> createOrUpdateCalendar(
        @RequestBody @Valid CalendarRequest request) {
        CalendarResponse response = calendarService.createOrUpdateCalendar(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    /**
     * 특정 회원의 캘린더 조회 API
     */

    @GetMapping("/{memberId}")
    @Operation(
        summary = "특정 회원의 캘린더 조회",
        description = "회원 ID를 기준으로 캘린더 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<CalendarResponse>>> getCalendarByMemberId(
        @PathVariable @NotNull Long memberId) {
        List<CalendarResponse> calendars = calendarService.getCalendarByMemberId(memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess(calendars));
    }

    /**
     * 캘린더 평가 상태 업데이트 API
     */
    @PatchMapping("/evaluation")
    @Operation(
        summary = "캘린더 평가 상태 업데이트",
        description = "회원 ID와 날짜를 기준으로 캘린더 평가 상태를 업데이트합니다."
    )
    public ResponseEntity<ApiResponse<CalendarResponse>> updateCalendarEvaluation(
        @RequestParam @NotNull Long memberId,
        @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam @NotNull EvaluationStatus evaluationStatus) {
        CalendarResponse updatedCalendar = calendarService.updateCalendarEvaluation(memberId, date,
            evaluationStatus);
        return ResponseEntity.ok(ApiResponse.onSuccess(updatedCalendar));
    }


    /**
     * 특정 회원의 한 달치 점 상태 조회 API
     */
    @GetMapping("/indicators")
    @Operation(
        summary = "특정 회원의 한 달치 점 상태 조회",
        description = "회원 ID와 기간(해당 달의 시작 날짜, 종료 날짜)을 기준으로 점 상태(IndicatorType)를 조회합니다."
    )
    public ResponseEntity<ApiResponse<Map<LocalDate, CalendarDayInfo.IndicatorType>>> getMonthlyIndicators(
        @RequestParam @NotNull Long memberId,
        @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, CalendarDayInfo.IndicatorType> indicators = calendarService.getMonthlyCalendarData(
            memberId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.onSuccess(indicators));
    }

}
