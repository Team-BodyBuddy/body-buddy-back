package BodyBuddy.demo.domain.trainer.service;

import BodyBuddy.demo.domain.member.dto.MemberInfoDto;
import BodyBuddy.demo.domain.trainer.Trainer;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponseDto;
import BodyBuddy.demo.domain.trainer.dto.TrainerSimpleResponseDto;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.status.ErrorStatus;
import BodyBuddy.demo.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    // 수정: 간단한 트레이너 정보를 반환
    public List<TrainerSimpleResponseDto> getTrainersByRegionAndGym(Long regionId, Long gymId) {
        List<Trainer> trainers = trainerRepository.findByRegionAndGym(regionId, gymId);

        if (trainers.isEmpty()) {
            throw new GeneralException(ErrorStatus.TRAINER_NOT_FOUND);
        }

        return trainers.stream()
                .map(trainer -> TrainerSimpleResponseDto.builder()
                        .id(trainer.getId())
                        .name(trainer.getName())
                        .gender(trainer.getGender().name())
                        .age((trainer.getAge()))
                        .build())
                .collect(Collectors.toList());
    }

    public TrainerResponseDto getTrainerDetails(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRAINER_NOT_FOUND));

        List<MemberInfoDto> members = trainer.getMembers().stream()
                .map(member -> MemberInfoDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .level(member.getLevel())
                        .build())
                .collect(Collectors.toList());

        return TrainerResponseDto.builder()
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
}
