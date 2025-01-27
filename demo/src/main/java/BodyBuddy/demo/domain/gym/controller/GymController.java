package BodyBuddy.demo.domain.gym.controller;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import BodyBuddy.demo.global.common.commonEnum.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gyms")
public class GymController {

    private final GymRepository gymRepository;

    @GetMapping("/regions")
    public ApiResponse<List<Region>> getAllRegions() {
        return ApiResponse.of(SuccessStatus.REGIONSUCCESS, List.of(Region.values()));
    }

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
