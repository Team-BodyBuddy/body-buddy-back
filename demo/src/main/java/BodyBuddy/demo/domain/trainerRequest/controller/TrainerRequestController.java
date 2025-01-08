package BodyBuddy.demo.domain.trainerRequest.controller;

import BodyBuddy.demo.domain.trainerRequest.RequestStatus;
import BodyBuddy.demo.domain.trainerRequest.dto.TrainerRequestDto;
import BodyBuddy.demo.domain.trainerRequest.dto.TrainerRequestResponseDto;
import BodyBuddy.demo.domain.trainerRequest.service.TrainerRequestService;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainer-requests")
public class TrainerRequestController {

    private final TrainerRequestService trainerRequestService;

    public TrainerRequestController(TrainerRequestService trainerRequestService) {
        this.trainerRequestService = trainerRequestService;
    }

    // 회원이 트레이너에게 요청 전송
    @PostMapping
    public ResponseEntity<ApiResponse<TrainerRequestResponseDto>> createTrainerRequest(
        @RequestBody TrainerRequestDto requestDto) {
        TrainerRequestResponseDto response = trainerRequestService.createRequest(requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 트레이너가 요청 조회
    @GetMapping("/{trainerId}")
    public ResponseEntity<ApiResponse<List<TrainerRequestResponseDto>>> getTrainerRequests(
        @PathVariable Long trainerId) {
        List<TrainerRequestResponseDto> response = trainerRequestService.getRequestsForTrainer(trainerId);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 요청 상태 변경 (수락/거절)
    @PatchMapping("/{requestId}")
    public ResponseEntity<ApiResponse<String>> updateRequestStatus(
        @PathVariable Long requestId,
        @RequestParam RequestStatus status) {
        trainerRequestService.updateRequestStatus(requestId, status);
        return ResponseEntity.ok(ApiResponse.onSuccess("Request status updated."));
    }
}
