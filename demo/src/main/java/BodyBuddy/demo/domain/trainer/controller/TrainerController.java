package BodyBuddy.demo.domain.trainer.controller;

import BodyBuddy.demo.domain.trainer.dto.TrainerRequest;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponse;
import BodyBuddy.demo.domain.trainer.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    /**
     * 지역 ID와 헬스장 ID로 필터링하여 간단한 트레이너 정보를 조회합니다.
     *
     * @param regionId 지역 ID
     * @param gymId    헬스장 ID
     * @return 간단한 트레이너 목록
     */
    @Operation(summary = "지역과 헬스장을 기준으로 간단한 트레이너 정보 조회 API",
            description = "지역 ID와 헬스장 ID를 기준으로 해당 지역/헬스장에 소속된 트레이너의 간단한 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "트레이너 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponse.Simple.class))),
            @ApiResponse(responseCode = "404",
                    description = "트레이너를 찾을 수 없음",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TrainerResponse.Simple>> getTrainers(
            @Parameter(description = "필터링할 지역의 ID", required = true) @RequestParam Long regionId,
            @Parameter(description = "필터링할 헬스장의 ID", required = true) @RequestParam Long gymId) {
        List<TrainerResponse.Simple> trainers = trainerService.getTrainersByRegionAndGym(regionId, gymId);
        return ResponseEntity.ok(trainers);
    }

    /**
     * 특정 트레이너의 ID로 상세 정보를 조회합니다.
     *
     * @param trainerId 트레이너 ID
     * @return 트레이너 상세 정보
     */
    @Operation(summary = "트레이너 상세 정보 조회 API",
            description = "트레이너 ID를 사용하여 특정 트레이너의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "트레이너 상세 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponse.Details.class))),
            @ApiResponse(responseCode = "404",
                    description = "트레이너를 찾을 수 없음",
                    content = @Content)
    })
    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerResponse.Details> getTrainerDetails(
            @Parameter(description = "조회할 트레이너의 고유 ID", required = true) @PathVariable Long trainerId) {
        TrainerResponse.Details trainerDetails = trainerService.getTrainerDetails(trainerId);
        return ResponseEntity.ok(trainerDetails);
    }

    /**
     * 트레이너 포트폴리오 수정 API.
     *
     * @param trainerId 트레이너 ID
     * @param requestDto 포트폴리오 수정 요청 DTO
     * @return 수정된 포트폴리오 응답 DTO
     */
    @Operation(summary = "트레이너 포트폴리오 수정 API",
            description = "트레이너 ID를 기반으로 포트폴리오를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "포트폴리오 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponse.UpdatedPortfolio.class))),
            @ApiResponse(responseCode = "404",
                    description = "트레이너를 찾을 수 없음",
                    content = @Content)
    })
    @PutMapping("/{trainerId}/portfolio")
    public ResponseEntity<TrainerResponse.UpdatedPortfolio> updatePortfolio(
            @Parameter(description = "수정할 트레이너의 ID", required = true) @PathVariable Long trainerId,
            @Valid @RequestBody TrainerRequest.UpdatePortfolio requestDto) {
        TrainerResponse.UpdatedPortfolio updatedPortfolio = trainerService.updatePortfolio(trainerId, requestDto);
        return ResponseEntity.ok(updatedPortfolio);
    }

    /**
     * 트레이너 지역, 키/몸무게 수정 API.
     *
     * @param trainerId 트레이너 ID
     * @param requestDto 지역, 키, 몸무게 수정 요청 DTO
     * @return 수정된 상세 정보 응답 DTO
     */
    @Operation(summary = "트레이너 지역 및 키/몸무게 수정 API",
            description = "트레이너 ID를 기반으로 지역, 키, 몸무게를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "지역, 키/몸무게 수정 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainerResponse.UpdatedDetails.class))),
            @ApiResponse(responseCode = "404",
                    description = "트레이너 또는 헬스장을 찾을 수 없음",
                    content = @Content)
    })
    @PutMapping("/{trainerId}/details")
    public ResponseEntity<TrainerResponse.UpdatedDetails> updateDetails(
            @Parameter(description = "수정할 트레이너의 ID", required = true) @PathVariable Long trainerId,
            @Valid @RequestBody TrainerRequest.UpdateDetails requestDto) {
        TrainerResponse.UpdatedDetails updatedDetails = trainerService.updateDetails(trainerId, requestDto);
        return ResponseEntity.ok(updatedDetails);
    }
}
