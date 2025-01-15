package BodyBuddy.demo.domain.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymResponseDto {
    private Long id;      // 헬스장 ID
    private String name;  // 헬스장 이름
}
