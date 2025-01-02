package BodyBuddy.demo.ranking.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 랭킹 응답 DTO (RankingResponseDto)
 * 클라이언트에게 랭킹 데이터를 전달
 */
@Data
@Builder
public class RankingResponseDto {
    private int rank;         // 순위
    private String nickname;  // 회원 닉네임
    private int level;        // 회원 레벨
    private int totalScore;   // 총 점수
}
