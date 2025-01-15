package BodyBuddy.demo.domain.gym.service;

import BodyBuddy.demo.domain.gym.dto.GymResponseDto;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    /**
     * 특정 지역 ID에 속한 헬스장 목록을 조회합니다.
     *
     * @param regionId 지역 ID
     * @return 헬스장 목록 DTO 리스트
     */
    public List<GymResponseDto> getGymsByRegion(Long regionId) {
        return gymRepository.findByRegionId(regionId).stream()
                .map(gym -> GymResponseDto.builder()
                        .id(gym.getId())
                        .name(gym.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
