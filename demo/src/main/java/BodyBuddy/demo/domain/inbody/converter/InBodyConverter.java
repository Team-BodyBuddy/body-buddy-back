package BodyBuddy.demo.domain.inbody.converter;

import BodyBuddy.demo.domain.inbody.dto.InBodyResponseDTO;
import BodyBuddy.demo.domain.inbody.dto.InBodyToRankingScoreDTO;
import BodyBuddy.demo.domain.inbody.entity.InBody;

import BodyBuddy.demo.global.common.commonEnum.InBodyStatus;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
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

    /**
     * 최신 인바디 데이터 + 직전 데이터 비교 DTO 변환
     */
    public InBodyResponseDTO.LatestData toLatestData(InBody current, InBody previous) {
        return new InBodyResponseDTO.LatestData(
            current.getWeight(),
            current.getMuscle(),
            current.getBodyFat(),
            InBodyStatus.compare(current.getWeight(), previous != null ? previous.getWeight() : current.getWeight()),
            InBodyStatus.compare(current.getMuscle(), previous != null ? previous.getMuscle() : current.getMuscle()),
            InBodyStatus.compare(current.getBodyFat(), previous != null ? previous.getBodyFat() : current.getBodyFat())
        );
    }

    /**
     * 최근 5개 인바디 데이터 리스트 DTO 변환
     */
    public List<InBodyResponseDTO.HistoryData> toHistoryDataList(List<InBody> inBodies) {
        return inBodies.stream()
            .map(inBody -> new InBodyResponseDTO.HistoryData(
                inBody.getWeight(),
                inBody.getMuscle(),
                inBody.getBodyFat()
            ))
            .collect(Collectors.toList());
    }
}
