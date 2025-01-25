package BodyBuddy.demo.domain.gym.controller;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
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
@RequestMapping("/api/gym")
public class GymController {

    private final GymRepository gymRepository;

    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getRegions() {
        return ResponseEntity.ok(List.of(Region.values()));
    }

    @GetMapping("/by-region")
    public ResponseEntity<List<Gym>> getGymsByRegion(@RequestParam Region region) {
        List<Gym> gyms = gymRepository.findByRegion(region);
        return ResponseEntity.ok(gyms);
    }
}
