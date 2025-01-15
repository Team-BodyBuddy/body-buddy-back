package BodyBuddy.demo.domain.trainer.dto;

import BodyBuddy.demo.domain.member.dto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 응답(Response) DTO를 정의한 클래스.
 * 모든 응답 관련 내부 클래스를 포함합니다.
 */
public class TrainerResponse {

    /**
     * 트레이너 상세 정보 응답 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Details {
        private Long id;                   // 트레이너 ID
        private String name;               // 이름
        private String gender;             // 성별
        private int height;                // 키
        private int weight;                // 몸무게
        private String regionName;         // 사는 지역
        private String gymName;            // 헬스장 이름
        private String portfolio;          // 약력
        private int badgeCount;            // 뱃지 개수
        private List<MemberInfoDto> members; // 관리 중인 회원 리스트
    }

    /**
     * 간단한 트레이너 정보 응답 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Simple {
        private Long id;       // 트레이너 ID
        private String name;   // 이름
        private String gender; // 성별
        private int age;       // 나이
    }

    /**
     * 지역, 키, 몸무게 수정 결과 응답 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatedDetails {
        private Long id;        // 트레이너 ID
        private String name;    // 이름
        private String gymName; // 소속 헬스장 이름
        private int height;     // 수정된 키
        private int weight;     // 수정된 몸무게
    }

    /**
     * 포트폴리오 수정 결과 응답 DTO.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdatedPortfolio {
        private Long id;           // 트레이너 ID
        private String name;       // 이름
        private String portfolio;  // 수정된 포트폴리오 내용
    }
}
