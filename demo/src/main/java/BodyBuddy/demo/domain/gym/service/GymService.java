package BodyBuddy.demo.domain.gym.service;

import BodyBuddy.demo.domain.gym.dto.GymDto;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 체육관 서비스 계층 (GymService)
 * 체육관 데이터 관리 및 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    /**
     * 모든 체육관 정보 조회
     * @return 체육관 DTO 리스트
     */
    public List<GymDto> getAllGyms() {
        return gymRepository.findAll().stream()
                .map(gym -> GymDto.builder()
                        .id(gym.getId())
                        .name(gym.getName())
                        .address(gym.getAddress())
                        .build())
                .collect(Collectors.toList());
    }
}
