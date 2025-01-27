package BodyBuddy.demo.domain.avatar.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RankingResponse {

    @Getter
    @Builder
    public static class GlobalRanking {
        private final int rank;
        private final String nickname;
        private final Long rankingScore;
        private final Long level;
    }

    @Getter
    @Builder
    public static class UpdateResponse {
        private final String nickname;
        private final Long rankingScore;
        private final Long updatedPoints;
        private final Long updatedExp;
    }
}
