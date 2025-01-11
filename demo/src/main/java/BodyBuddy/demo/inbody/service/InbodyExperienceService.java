package BodyBuddy.demo.inbody.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 인바디 데이터를 비교하여 경험치를 계산하는 서비스
 */
@Service
@RequiredArgsConstructor
public class InbodyExperienceService {

    // 체지방량 및 골격근량 표준 범위 데이터
    private static final Map<Integer, Double[]> BODY_FAT_STANDARD = Map.of(
            155, new Double[]{27.0, 31.0},
            160, new Double[]{31.0, 35.0},
            165, new Double[]{33.0, 37.0},
            170, new Double[]{35.0, 39.0},
            175, new Double[]{37.0, 41.0},
            180, new Double[]{39.0, 43.0},
            185, new Double[]{41.0, 45.0}
    );

    private static final Map<Integer, Double[]> MUSCLE_STANDARD = Map.of(
            155, new Double[]{19.0, 22.0},
            160, new Double[]{21.0, 24.0},
            165, new Double[]{23.0, 26.0},
            170, new Double[]{24.0, 27.0},
            175, new Double[]{25.0, 28.0},
            180, new Double[]{26.0, 29.0},
            185, new Double[]{27.0, 30.0}
    );

    /**
     * 인바디 데이터 비교 및 경험치 계산
     * @param previousFat 이전 체지방량
     * @param currentFat 현재 체지방량
     * @param previousMuscle 이전 골격근량
     * @param currentMuscle 현재 골격근량
     * @param height 사용자 키
     * @return 계산된 경험치
     */
    public int calculateExperience(double previousFat, double currentFat, double previousMuscle, double currentMuscle, int height) {
        int fatExperience = calculateFatExperience(currentFat, height);
        int muscleExperience = calculateMuscleExperience(currentMuscle, height);
        return fatExperience + muscleExperience;
    }

    /**
     * 체지방량 변화에 따른 경험치 계산
     * @param currentFat 현재 체지방량
     * @param height 사용자 키
     * @return 체지방량 경험치
     */
    private int calculateFatExperience(double currentFat, int height) {
        Double[] standardRange = BODY_FAT_STANDARD.getOrDefault(height, new Double[]{0.0, 0.0});
        double lowerBound = standardRange[0] * 0.85; // 표준 -15%
        double upperBound = standardRange[1] * 1.15; // 표준 +15%

        if (currentFat < lowerBound) {
            return (int) (30 * 3); // 표준 -15% 이하
        } else if (currentFat < standardRange[0]) {
            return (int) (30 * 2); // 표준 -15% < 현재 < 표준
        } else if (currentFat <= standardRange[1]) {
            return (int) (30 * 1.7); // 표준 범위 내
        } else if (currentFat <= upperBound) {
            return (int) (30 * 1.3); // 표준 < 현재 < 표준 +15%
        } else {
            return 30; // 표준 +15% 이상
        }
    }

    /**
     * 골격근량 변화에 따른 경험치 계산
     * @param currentMuscle 현재 골격근량
     * @param height 사용자 키
     * @return 골격근량 경험치
     */
    private int calculateMuscleExperience(double currentMuscle, int height) {
        Double[] standardRange = MUSCLE_STANDARD.getOrDefault(height, new Double[]{0.0, 0.0});
        double lowerBound = standardRange[0] * 0.85; // 표준 -15%
        double upperBound = standardRange[1] * 1.15; // 표준 +15%

        if (currentMuscle > upperBound) {
            return (int) (100 * 3); // 표준 +15% 이상
        } else if (currentMuscle > standardRange[1]) {
            return (int) (100 * 2); // 표준 < 현재 < 표준 +15%
        } else if (currentMuscle >= standardRange[0]) {
            return (int) (100 * 1.7); // 표준 범위 내
        } else if (currentMuscle >= lowerBound) {
            return (int) (100 * 1.3); // 표준 -15% < 현재 < 표준
        } else {
            return 100; // 표준 -15% 이하
        }
    }
}
