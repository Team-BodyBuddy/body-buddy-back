package BodyBuddy.demo.domain.gym.controller;

import BodyBuddy.demo.domain.gym.dto.GymResponseDto;
import BodyBuddy.demo.domain.gym.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    @GetMapping
    public ResponseEntity<List<GymResponseDto>> getGymsByRegion(@RequestParam Long regionId) {
        List<GymResponseDto> gyms = gymService.getGymsByRegion(regionId);
        return ResponseEntity.ok(gyms);
    }
}
