package BodyBuddy.demo.domain.trainer.controller;

import BodyBuddy.demo.domain.trainer.dto.TrainerResponseDto;
import BodyBuddy.demo.domain.trainer.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<TrainerResponseDto>> getTrainers(
            @RequestParam Long regionId,
            @RequestParam Long gymId) {
        List<TrainerResponseDto> trainers = trainerService.getTrainersByRegionAndGym(regionId, gymId);
        return ResponseEntity.ok(trainers);
    }
}
