package BodyBuddy.demo.domain.trainer.service;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.domain.matchingAuthentication.repository.MatchingAuthenticationRepository;
import BodyBuddy.demo.global.apiPayload.code.error.GymErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.common.commonEnum.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.trainer.dto.TrainerMyPageResponseDto;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.domain.trainer.converter.TrainerConverter;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponse;

import BodyBuddy.demo.global.common.commonEnum.AuthenticationRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final MatchingAuthenticationRepository matchingAuthenticationRepository;
    private final TrainerConverter trainerConverter;
    private final GymRepository gymRepository;

    public TrainerResponse getTrainerDetails(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));
        return trainerConverter.convertToTrainerResponse(trainer);
    }

    /**
     * 트레이너 마이페이지 정보 조회
     */
    @Transactional(readOnly = true)
    public TrainerMyPageResponseDto getTrainerMyPage(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));

        long pendingRequestCount = matchingAuthenticationRepository.countByTrainerAndStatus(trainer,
            AuthenticationRequest.PENDING);

        return TrainerMyPageResponseDto.from(trainer, pendingRequestCount);
    }

    /**
     * 트레이너 프로필 이미지 수정
     */
    public void updateProfileImage(Long trainerId, String newProfileImageUrl) {
        Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));

        trainer.updateProfileImage(newProfileImageUrl);
        trainerRepository.save(trainer);
    }

    /**
     * 트레이너 프로필 정보(헬스장, 지역, 키, 몸무게) 수정
     */
    public void updateTrainerProfile(Long trainerId, Long gymId, Region region, Float height,
        Float weight) {
        Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));

        Gym gym = gymRepository.findById(gymId)
            .orElseThrow(() -> new BodyBuddyException(GymErrorCode.GYM_NOT_FOUND));

        trainer.updateProfile(gym, region, height, weight);

        trainerRepository.save(trainer);
    }
}
