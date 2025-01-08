package BodyBuddy.demo.domain.trainerRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerRequestDto {
    private Long trainerId;
    private Long memberId;
}
