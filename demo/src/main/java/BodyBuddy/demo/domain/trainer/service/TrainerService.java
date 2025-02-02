package BodyBuddy.demo.domain.trainer.service;

import BodyBuddy.demo.domain.trainer.converter.TrainerConverter;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponse;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerConverter trainerConverter;

    public TrainerResponse getTrainerDetails(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));
        return trainerConverter.convertToTrainerResponse(trainer);
    }
}
