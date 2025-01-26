package BodyBuddy.demo.domain.gym.service;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public Gym validateGym(Long gymId) {
        return gymRepository.findById(gymId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 헬스장입니다."));
    }
}
