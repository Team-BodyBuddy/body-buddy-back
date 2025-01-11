package BodyBuddy.demo.domain.trainer.service;

import BodyBuddy.demo.domain.trainer.Trainer;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponseDto;
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

    public List<TrainerResponseDto> getTrainersByRegionAndGym(Long regionId, Long gymId) {
        List<Trainer> trainers = trainerRepository.findByRegionAndGym(regionId, gymId);

        if (trainers.isEmpty()) {
            throw new GeneralException(ErrorStatus.TRAINER_NOT_FOUND);
        }

        return trainers.stream()
                .map(trainer -> TrainerResponseDto.builder()
                        .id(trainer.getId())
                        .name(trainer.getName())
                        .portfolio(trainer.getPortfolio())
                        .badgeCount(trainer.getBadgeCount())
                        .gymName(trainer.getGym().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
