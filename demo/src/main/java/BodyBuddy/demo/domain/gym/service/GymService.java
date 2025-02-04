package BodyBuddy.demo.domain.gym.service;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.global.apiPayload.code.error.CommonErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public Gym validateGym(Long gymId) {
        return gymRepository.findById(gymId)
            .orElseThrow(() -> new BodyBuddyException(CommonErrorCode.GYM_NOT_FOUND));
    }
}
