package BodyBuddy.demo.domain.inbody.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InBodyToRankingScoreDTO {
    private Long memberId;
    private Float currentWeight;
    private Float currentMuscle;
    private Float currentBodyFat;
    private Float previousWeight;
    private Float previousMuscle;
    private Float previousBodyFat;
    private int rankingScore;
}
