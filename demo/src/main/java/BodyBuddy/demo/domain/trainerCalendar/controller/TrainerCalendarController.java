package BodyBuddy.demo.domain.trainerCalendar.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.classSchedule.dto.ClassScheduleResponse;
import BodyBuddy.demo.domain.classSchedule.entity.ClassSchedule;
import BodyBuddy.demo.domain.dailyMemo.dto.DailyMemoResponse;
import BodyBuddy.demo.domain.dailyMemo.entity.DailyMemo;

import BodyBuddy.demo.domain.trainerCalendar.dto.TrainerCalendarResponse;
import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;
import BodyBuddy.demo.domain.trainerCalendar.service.TrainerCalendarService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("trainer-calendar")
@RequiredArgsConstructor
@Tag(name = "트레이너 캘린더", description = "트레이너 캘린더 관리 API")
public class TrainerCalendarController {

	private final TrainerCalendarService trainerCalendarService;

	/**
	 * 특정 트레이너, 회원의 특정 날짜의 캘린더 조회
	 */
	@Operation(summary = "특정 날짜의 캘린더 조회", description = "트레이너의 특정 회원의 특정 날짜 캘린더를 조회하거나 생성합니다.")
	@PostMapping("/date")
	public ResponseEntity<ApiResponse<TrainerCalendarResponse>> getOrCreateCalendarByDate(
		@RequestParam Long trainerId,
		@RequestParam Long memberId,
		@RequestParam String date
	) {
		LocalDate parsedDate = LocalDate.parse(date);
		TrainerCalendar calendar = trainerCalendarService.getOrCreateCalendarByDate(trainerId, memberId, parsedDate);
		return ResponseEntity.ok(ApiResponse.onSuccess(TrainerCalendarResponse.from(calendar)));
	}

	/**
	 * 특정 캘린더(calendarId)에 새로운 수업 추가
	 */
	@Operation(summary = "수업 추가", description = "특정 트레이너 캘린더에 수업을 추가합니다.")
	@PostMapping("/{calendarId}/class-schedule")
	public ResponseEntity<ApiResponse<ClassScheduleResponse>> addClassSchedule(
		@PathVariable Long calendarId,
		@RequestBody String description
	) {
		ClassSchedule schedule = trainerCalendarService.addClassSchedule(calendarId, description);
		return ResponseEntity.ok(ApiResponse.onSuccess(ClassScheduleResponse.from(schedule)));
	}

	/**
	 * 특정 수업(classScheduleId) 삭제
	 */
	@Operation(summary = "수업 삭제", description = "특정 수업을 삭제합니다.")
	@DeleteMapping("/class-schedule/{classScheduleId}")
	public ResponseEntity<ApiResponse<Void>> removeClassSchedule(@PathVariable Long classScheduleId) {
		trainerCalendarService.removeClassSchedule(classScheduleId);
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	/**
	 * 특정 수업의 완료 여부 토글
	 */
	@Operation(summary = "수업 완료 여부 토글", description = "특정 수업의 완료 상태를 변경합니다.")
	@PatchMapping("/class-schedule/{classScheduleId}/toggle")
	public ResponseEntity<ApiResponse<ClassScheduleResponse>> toggleClassCompletion(
		@PathVariable Long classScheduleId
	) {
		ClassSchedule schedule = trainerCalendarService.toggleClassCompletion(classScheduleId);
		return ResponseEntity.ok(ApiResponse.onSuccess(ClassScheduleResponse.from(schedule)));
	}

	/**
	 * 특정 캘린더(calendarId)에 메모 추가 또는 업데이트
	 */
	@Operation(summary = "일일 메모 추가 또는 수정", description = "특정 트레이너 캘린더에 메모를 추가하거나 수정합니다.")
	@PatchMapping("/{calendarId}/daily-memo")
	public ResponseEntity<ApiResponse<DailyMemoResponse>> updateDailyMemo(
		@PathVariable Long calendarId,
		@RequestBody String memo
	) {
		DailyMemo updatedMemo = trainerCalendarService.updateDailyMemo(calendarId, memo);
		return ResponseEntity.ok(ApiResponse.onSuccess(DailyMemoResponse.
			from(updatedMemo)));
	}
}
