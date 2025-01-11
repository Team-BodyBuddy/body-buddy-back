package BodyBuddy.demo.domain.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerResponseDto {
    private Long id;           // 트레이너 ID
    private String name;       // 트레이너 이름
    private String portfolio;  // 트레이너 약력
    private int badgeCount;    // 보유 뱃지 수
    private String gymName;    // 소속 헬스장 이름
}
