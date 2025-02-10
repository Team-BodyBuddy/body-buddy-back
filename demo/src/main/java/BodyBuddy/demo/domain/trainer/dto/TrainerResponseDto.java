package BodyBuddy.demo.domain.trainer.dto;
import lombok.Builder;

@Builder
public record TrainerResponseDto(
        Long id,
        String realName,
        int age
) {}
