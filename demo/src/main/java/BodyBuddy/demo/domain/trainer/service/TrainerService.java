package BodyBuddy.demo.domain.trainer.service;

import BodyBuddy.demo.domain.gym.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.domain.member.dto.MemberInfoDto;
import BodyBuddy.demo.domain.trainer.Trainer;
import BodyBuddy.demo.domain.trainer.dto.*;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.status.ErrorStatus;
import BodyBuddy.demo.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 트레이너 관련 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final GymRepository gymRepository;

    /**
     * 지역과 헬스장 기준으로 간단한 트레이너 목록을 조회합니다.
     *
     * @param regionId 지역 ID
     * @param gymId    헬스장 ID
     * @return 간단한 트레이너 정보 리스트
     */
    public List<TrainerResponse.Simple> getTrainersByRegionAndGym(Long regionId, Long gymId) {
        List<Trainer> trainers = trainerRepository.findByRegionAndGym(regionId, gymId);

        if (trainers.isEmpty()) {
            throw new GeneralException(ErrorStatus.TRAINER_NOT_FOUND);
        }

        return trainers.stream()
                .map(trainer -> TrainerResponse.Simple.builder()
                        .id(trainer.getId())
                        .name(trainer.getName())
                        .gender(trainer.getGender().name())
                        .age(trainer.getAge())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 특정 트레이너의 상세 정보를 조회합니다.
     *
     * @param trainerId 트레이너 ID
     * @return 트레이너 상세 정보
     */
    public TrainerResponse.Details getTrainerDetails(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRAINER_NOT_FOUND));

        List<MemberInfoDto> members = trainer.getMembers().stream()
                .map(member -> MemberInfoDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .level(member.getLevel())
                        .build())
                .collect(Collectors.toList());

        return TrainerResponse.Details.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .gender(trainer.getGender().name())
                .height(trainer.getHeight())
                .weight(trainer.getWeight())
                .regionName(trainer.getGym().getRegion().getName())
                .gymName(trainer.getGym().getName())
                .portfolio(trainer.getPortfolio())
                .badgeCount(trainer.getBadgeCount())
                .members(members)
                .build();
    }

    /**
     * 트레이너의 포트폴리오를 수정합니다.
     *
     * @param trainerId 트레이너 ID
     * @param requestDto 포트폴리오 수정 요청 DTO
     * @return 수정된 포트폴리오 응답 DTO
     */
    public TrainerResponse.UpdatedPortfolio updatePortfolio(Long trainerId, TrainerRequest.UpdatePortfolio requestDto) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRAINER_NOT_FOUND));

        trainer.setPortfolio(requestDto.getPortfolio());
        trainerRepository.save(trainer);

        return TrainerResponse.UpdatedPortfolio.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .portfolio(trainer.getPortfolio())
                .build();
    }

    /**
     * 트레이너의 지역, 키, 몸무게를 수정합니다.
     *
     * @param trainerId 트레이너 ID
     * @param requestDto 수정 요청 DTO
     * @return 수정된 상세 정보 응답 DTO
     */
    public TrainerResponse.UpdatedDetails updateDetails(Long trainerId, TrainerRequest.UpdateDetails requestDto) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRAINER_NOT_FOUND));

        Gym gym = gymRepository.findById(requestDto.getGymId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.GYM_NOT_FOUND));

        trainer.setGym(gym);
        trainer.setHeight(requestDto.getHeight());
        trainer.setWeight(requestDto.getWeight());

        trainerRepository.save(trainer);

        return TrainerResponse.UpdatedDetails.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .gymName(gym.getName())
                .height(trainer.getHeight())
                .weight(trainer.getWeight())
                .build();
    }
}
