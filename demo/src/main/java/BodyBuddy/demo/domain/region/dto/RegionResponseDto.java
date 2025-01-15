package BodyBuddy.demo.domain.region.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionResponseDto {
    private Long id;      // 지역 ID
    private String name;  // 지역 이름
}
