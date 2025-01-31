package BodyBuddy.demo.domain.trainer.dto;
import lombok.Builder;

@Builder
public record TrainerResponseDto(
        String realName,
        int age
) {}
