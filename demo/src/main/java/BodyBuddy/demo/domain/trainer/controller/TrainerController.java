package BodyBuddy.demo.domain.trainer.controller;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.domain.trainer.converter.TrainerConverter;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponse;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponseDto;
import BodyBuddy.demo.domain.trainer.service.TrainerService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainers")
public class TrainerController {

    private final GymRepository gymRepository;
    private final TrainerConverter trainerConverter;
    private final TrainerService trainerService;

    /**
     * 특정 헬스장에 등록된 트레이너 목록을 반환하는 API (실명 및 나이 포함)
     */
    @GetMapping("/list/{gymId}")
    public ApiResponse<List<TrainerResponseDto>> getTrainersByGym(@PathVariable Long gymId) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("해당 헬스장이 존재하지 않습니다."));

        List<TrainerResponseDto> trainers = trainerConverter.convertToTrainerDtoList(gym.getTrainers());

        return ApiResponse.of(SuccessStatus.TRAINERS_BY_GYM_SUCCESS, trainers);
    }

    @GetMapping("/{trainerId}")
    public ApiResponse<TrainerResponse> getTrainerDetails(@PathVariable Long trainerId) {
        return ApiResponse.of(SuccessStatus.TRAINER_INFO_SUCCESS, trainerService.getTrainerDetails(trainerId));
    }
}
