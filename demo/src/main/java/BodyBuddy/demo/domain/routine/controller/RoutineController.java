package BodyBuddy.demo.domain.routine.controller;

import BodyBuddy.demo.domain.routine.dto.RoutineResponse;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
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
    public ResponseEntity<ApiResponse<RoutineResponse>> addRoutine(
        @RequestBody @Valid RoutineRequest request) {
        RoutineResponse response = routineService.addRoutine(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    /**
     * 루틴 조회 API
     */
    @GetMapping("/api/routines")
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
    public ResponseEntity<ApiResponse<Void>> completedRoutine(@PathVariable Long routineId) {
        routineService.completedRoutine(routineId);
        return ResponseEntity.ok().build();
    }

    /**
     * 루틴 삭제
     */
    @DeleteMapping("/{routineId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

}
