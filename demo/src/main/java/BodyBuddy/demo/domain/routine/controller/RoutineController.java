package BodyBuddy.demo.domain.routine.controller;

import BodyBuddy.demo.domain.routine.dto.RoutineResponse;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import BodyBuddy.demo.domain.routine.Routine;
import BodyBuddy.demo.domain.routine.dto.RoutineRequest;
import BodyBuddy.demo.domain.routine.repository.RoutineRepository;
import BodyBuddy.demo.domain.routine.service.RoutineService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    @Autowired
    private RoutineRepository routineRepository;
    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    /**
     * 루틴 추가 API
     */
    @PostMapping
    @Operation(
        summary = "루틴 추가",
        description = "회원 ID와 날짜, 루틴의 이름 및 유형을 입력하여 새로운 루틴을 추가합니다."
    )
    public ResponseEntity<ApiResponse<RoutineResponse>> addRoutine(
        @RequestBody @Valid RoutineRequest request) {
        RoutineResponse response = routineService.addRoutine(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    /**
     * 루틴 조회 API
     */
    @GetMapping("/api/routines")
    @Operation(
        summary = "특정 회원의 특정 날짜 루틴 조회",
        description = "회원 ID와 날짜를 기준으로 해당 회원의 특정 날짜에 설정된 모든 루틴을 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<RoutineResponse>>> getRoutinesByDate(
        @RequestParam Long memberId,
        @RequestParam LocalDate date) {

        List<RoutineResponse> responses = routineService.getRoutinesByDate(memberId, date);
        return ResponseEntity.ok(ApiResponse.onSuccess(responses));
    }

    /**
     * 루틴 완료 처리 API
     */
    @PostMapping("/api/routines/{routineId}/complete")
    @Operation(
        summary = "루틴 완료 처리",
        description = "루틴 ID를 기반으로 해당 루틴을 완료 상태로 변경합니다."
    )
    public ResponseEntity<ApiResponse<Void>> completedRoutine(@PathVariable Long routineId) {
        routineService.completedRoutine(routineId);
        return ResponseEntity.ok().build();
    }

    /**
     * 루틴 삭제
     */
    @DeleteMapping("/{routineId}")
    @Operation(
        summary = "루틴 삭제",
        description = "루틴 ID를 기반으로 해당 루틴을 삭제합니다."
    )
    public ResponseEntity<ApiResponse<Void>> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

}
