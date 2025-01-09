package BodyBuddy.demo.domain.trainerRequest.dto;

import BodyBuddy.demo.domain.trainerRequest.RequestStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainerRequestResponseDto {
    private Long requestId;
    private String memberNickname;
    private String trainerName;
    private RequestStatus status;
    private LocalDateTime requestDate;
}