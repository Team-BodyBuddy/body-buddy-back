package BodyBuddy.demo.domain.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerRequestDto {
    private Long regionId; // 선택된 지역 ID
    private Long gymId;    // 선택된 헬스장 ID
}
