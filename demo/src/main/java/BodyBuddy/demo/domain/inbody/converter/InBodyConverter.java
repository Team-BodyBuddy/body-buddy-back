package BodyBuddy.demo.domain.inbody.converter;

import BodyBuddy.demo.domain.inbody.dto.InBodyToRankingScoreDTO;
import BodyBuddy.demo.domain.inbody.entity.InBody;

import java.util.List;

public class InBodyConverter {

    public static InBodyToRankingScoreDTO toRankingScoreDTO(List<InBody> inBodyData, int calculatedScore) {
        InBody current = inBodyData.get(0);
        InBody previous = inBodyData.get(1);

        return InBodyToRankingScoreDTO.builder()
            .memberId(current.getMember().getId())
            .currentWeight(current.getWeight())
            .currentMuscle(current.getMuscle())
            .currentBodyFat(current.getBodyFat())
            .previousWeight(previous.getWeight())
            .previousMuscle(previous.getMuscle())
            .previousBodyFat(previous.getBodyFat())
            .rankingScore(calculatedScore)
            .build();
    }
}
