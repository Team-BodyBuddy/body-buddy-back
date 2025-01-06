package BodyBuddy.demo.gym.controller;

import BodyBuddy.demo.gym.dto.GymDto;
import BodyBuddy.demo.gym.service.GymService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 체육관 컨트롤러 (GymController)
 * 체육관 관련 RESTful API를 제공
 */
@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
@Tag(name = "GYM API", description = "GYM 관련 API")
public class GymController {

    private final GymService gymService;

    /**
     * 모든 체육관 정보 조회 API
     *
     * @return 체육관 DTO 리스트
     */
    @GetMapping
    @Operation(summary = "모든 헬스장 조회", description = "등록된 모든 헬스장의 목록을 조회합니다.")
    public ResponseEntity<List<GymDto>> getAllGyms() {
        return ResponseEntity.ok(gymService.getAllGyms());
    }
}
