package BodyBuddy.demo.domain.ranking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 랭킹 요청 DTO (RankingRequestDto)
 * 클라이언트의 랭킹 요청 데이터를 전달
 */
@Data
public class RankingRequestDto {
    @NotNull
    private Long memberId; // 요청하는 회원 ID

    @NotNull
    private Long gymId; // 요청하는 체육관 ID
}
