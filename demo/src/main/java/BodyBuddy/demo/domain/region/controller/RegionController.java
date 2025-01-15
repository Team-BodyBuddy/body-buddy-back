package BodyBuddy.demo.domain.region.controller;

import BodyBuddy.demo.domain.region.dto.RegionResponseDto;
import BodyBuddy.demo.domain.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    // 지역 목록 조회
    @GetMapping
    public ResponseEntity<List<RegionResponseDto>> getAllRegions() {
        List<RegionResponseDto> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }
}
