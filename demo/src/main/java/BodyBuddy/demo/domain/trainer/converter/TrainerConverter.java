package BodyBuddy.demo.domain.trainer.converter;

import BodyBuddy.demo.domain.trainer.dto.TrainerResponseDto;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainerConverter {

    /**
     * 트레이너 엔티티를 TrainerResponseDto 리스트로 변환
     */
    public List<TrainerResponseDto> convertToTrainerDtoList(List<Trainer> trainers) {
        return trainers.stream()
                .map(this::convertToTrainerDto)
                .collect(Collectors.toList());
    }

    /**
     * 트레이너 엔티티를 TrainerResponseDto로 변환
     */
    public TrainerResponseDto convertToTrainerDto(Trainer trainer) {
        return TrainerResponseDto.builder()
                .realName(trainer.getRealName())
                .age(calculateAge(trainer.getBirthday()))
                .build();
    }

    /**
     * 생년월일을 기반으로 현재 만 나이 계산
     */
    private int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
