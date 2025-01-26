package BodyBuddy.demo.domain.avatar.converter;

import BodyBuddy.demo.domain.avatar.dto.RankingResponse;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import org.springframework.stereotype.Component;

@Component
public class RankingConverter {

    public RankingResponse.GlobalRanking convertToGlobalRanking(Avatar avatar, int rank) {
        return RankingResponse.GlobalRanking.builder()
            .rank(rank)
            .nickname(avatar.getMember().getNickname())
            .rankingScore(avatar.getRankingScore())
            .level(avatar.getLevel())
            .build();
    }

    public RankingResponse.UpdateResponse convertToUpdateResponse(Avatar avatar, int points, int exp) {
        return RankingResponse.UpdateResponse.builder()
            .nickname(avatar.getMember().getNickname())
            .rankingScore(avatar.getRankingScore())
            .updatedPoints((long) points)
            .updatedExp((long) exp)
            .build();
    }
}
