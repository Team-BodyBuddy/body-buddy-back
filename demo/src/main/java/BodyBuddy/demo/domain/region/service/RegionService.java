package BodyBuddy.demo.domain.region.service;

import BodyBuddy.demo.domain.region.dto.RegionResponseDto;
import BodyBuddy.demo.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    /**
     * 모든 지역 목록을 조회합니다.
     *
     * @return 지역 목록 DTO 리스트
     */
    public List<RegionResponseDto> getAllRegions() {
        return regionRepository.findAll().stream()
                .map(region -> RegionResponseDto.builder()
                        .id(region.getId())
                        .name(region.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
