package BodyBuddy.demo.domain.routine.controller;

import java.time.LocalDate;
import java.util.List;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.routine.dto.RoutineRequestDto;
import BodyBuddy.demo.domain.routine.dto.RoutineResponseDto;
import BodyBuddy.demo.domain.routine.service.RoutineService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routine")
@RequiredArgsConstructor
@Tag(name="루틴 관리",description = "사용자의 운동 루틴 및 수업을 추가, 조회, 삭제, 토글하는 API")
public class RoutineController {

	private final RoutineService routineService;

	/**
	 * 루틴/수업 추가
	 */
	@Operation(summary = "루틴 추가", description = "회원의 루틴 또는 수업을 추가합니다.")
	@PostMapping("/add")
	public ResponseEntity<ApiResponse<CalendarResponse>> addRoutine(@RequestBody @Valid RoutineRequestDto dto) {
		CalendarResponse response = routineService.addRoutine(dto);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 루틴/수업 삭제
	 */
	@Operation(summary = "루틴 삭제", description = "회원의 루틴 또는 수업을 삭제합니다.")
	@PostMapping("/remove")
	public ResponseEntity<ApiResponse<CalendarResponse>> removeRoutine(@RequestBody @Valid RoutineRequestDto dto) {
		CalendarResponse response = routineService.removeRoutine(dto);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	/**
	 * 특정 회원의 특정 날짜의 루틴/수업 조회
	 */
	@Operation(summary = "특정 날짜의 루틴 조회", description = "특정 회원의 특정 날짜에 등록된 루틴 또는 수업을 조회합니다.")
	@GetMapping("/{memberId}/{date}")
	public ResponseEntity<ApiResponse<List<RoutineResponseDto>>> getRoutines(
		@PathVariable Long memberId,
		@PathVariable String date
	) {
		LocalDate localDate = LocalDate.parse(date);
		List<RoutineResponseDto> routines = routineService.getRoutines(memberId, localDate);
		return ResponseEntity.ok(ApiResponse.onSuccess(routines));
	}


	/**
	 * 루틴/수업의 수행 여부 토글
	 */
	@Operation(summary = "루틴 완료 여부 토글", description = "특정 루틴의 수행 여부를 토글합니다.")
	@PatchMapping("/{routineId}/toggle")
	public ResponseEntity<ApiResponse<RoutineResponseDto>> toggleRoutineCompletion(
		@PathVariable Long routineId
	) {
		RoutineResponseDto toggledRoutine = routineService.toggleRoutineCompletion(routineId);
		return ResponseEntity.ok(ApiResponse.onSuccess(toggledRoutine));
	}
}
