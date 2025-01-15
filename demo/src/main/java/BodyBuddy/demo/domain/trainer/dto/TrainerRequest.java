package BodyBuddy.demo.domain.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 요청(Request) DTO를 정의한 클래스.
 * 모든 요청 관련 내부 클래스를 포함합니다.
 */
public class TrainerRequest {

    /**
     * 트레이너 지역, 키, 몸무게 수정 요청 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateDetails {
        private Long gymId;  // 선택된 헬스장 ID
        private int height;  // 수정할 키 (cm)
        private int weight;  // 수정할 몸무게 (kg)
    }

    /**
     * 트레이너 포트폴리오 수정 요청 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatePortfolio {
        private String portfolio; // 수정할 포트폴리오 내용
    }

    /**
     * 지역 및 헬스장 기준으로 트레이너 필터링 요청 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FilterByRegionAndGym {
        private Long regionId; // 선택된 지역 ID
        private Long gymId;    // 선택된 헬스장 ID
    }
}
