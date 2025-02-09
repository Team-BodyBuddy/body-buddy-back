package BodyBuddy.demo.domain.gym.controller;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import BodyBuddy.demo.global.common.commonEnum.Region;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gyms")
@Tag(name = "헬스장 관리", description = "헬스장 조회 API")
public class GymController {

    private final GymRepository gymRepository;
    @Operation(summary = "지역 리스트 조회", description = "등록된 지역 리스트를 반환합니다.")
    @GetMapping("/regions")
    public ApiResponse<List<Region>> getAllRegions() {
        return ApiResponse.of(SuccessStatus.REGIONSUCCESS, List.of(Region.values()));
    }
    @Operation(summary = "헬스장 조회", description = "특정 지역에 속한 헬스장 목록을 반환합니다. 지역이 없으면 전체 목록을 반환합니다.")
    @GetMapping
    public ApiResponse<List<Gym>> getGymsByRegion(@RequestParam(required = false) Region region) {
        List<Gym> gyms;
        if (region != null) {
            gyms = gymRepository.findByRegion(region);
        } else {
            gyms = gymRepository.findAll(); // 모든 체육관을 조회하는 메서드가 필요합니다.
        }
        return ApiResponse.of(SuccessStatus.GYMSUCCESS, gyms);
    }
}
