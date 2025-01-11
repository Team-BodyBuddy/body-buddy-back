package BodyBuddy.demo.domain.trainer.dto;

import BodyBuddy.demo.domain.member.dto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerResponseDto {
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
