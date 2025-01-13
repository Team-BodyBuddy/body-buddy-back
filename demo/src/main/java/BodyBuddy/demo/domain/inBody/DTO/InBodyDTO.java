package BodyBuddy.demo.domain.inBody.DTO;

import BodyBuddy.demo.global.common.InBodyStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InBodyDTO{

    private RecentComparison recentComparison; // 최근 비교 데이터
    private List<WeightHistory> weightHistory; // 체중 기록

    /**
     * 최근 비교 데이터 DTO
     */

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecentComparison {
        private Float currentWeight;   // 현재 체중
        private Float previousWeight;  // 이전 체중
        private Float weightChange;    // 체중 변화량
        private InBodyStatus weightStatus; // 체중 변화 상태

        private Float currentMuscle;   // 현재 골격근량
        private Float previousMuscle;  // 이전 골격근량
        private Float muscleChange;    // 골격근량 변화량
        private InBodyStatus muscleStatus; // 골격근량 변화 상태

        private Float currentBodyFat;  // 현재 체지방률
        private Float previousBodyFat; // 이전 체지방률
        private Float bodyFatChange;   // 체지방률 변화량
        private InBodyStatus bodyFatStatus; // 체지방률 변화 상태
    }

    /**
     * 체중 기록 DTO
     */

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeightHistory {
        private LocalDate date; // 기록 날짜
        private Float weight;   // 체중
    }

}
