package BodyBuddy.demo.global.common.service;

import BodyBuddy.demo.global.common.entity.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 경험치 관련 서비스 계층 (MemberExperienceService)
 */
@Service
@RequiredArgsConstructor
public class MemberExperienceService {

    private static final int BASE_EXP = 100; // 기본 경험치
    private static final double GROWTH_FACTOR = 1.5; // 경험치 지수 성장 계수

    /**
     * 경험치 계산 로직
     * @param level 회원 레벨
     * @param activityType 활동 유형
     * @param streakDays 연속 운동 일수
     * @param rank 랭킹
     * @return 계산된 경험치
     */
    public int calculateExperience(int level, ActivityType activityType, int streakDays, int rank) {
        int baseExp = (int) (BASE_EXP * Math.pow(level, GROWTH_FACTOR));

        int activityExp = switch (activityType) {
            case GOAL -> 100;
            case ACCUMULATION -> 50;
            case DIFFICULTY -> 200;
            case COMPETITION -> 500;
            default -> 0;
        };

        int streakBonus = streakDays >= 7 ? (int) (baseExp * 0.1) : 0;
        int rankBonus = rank <= 10 ? 500 : rank <= 50 ? 250 : 100;

        return baseExp + activityExp + streakBonus + rankBonus;
    }

    /**
     * 레벨업 필요 경험치 계산
     * @param level 회원 레벨
     * @return 다음 레벨에 필요한 경험치
     */
    public int calculateExpForNextLevel(int level) {
        return (int) (BASE_EXP * Math.pow(level, GROWTH_FACTOR));
    }

    /**
     * 레벨업 여부 확인
     * @param currentExp 현재 경험치
     * @param totalExpForNextLevel 다음 레벨 경험치 요구량
     * @return 레벨업 여부
     */
    public boolean checkLevelUp(int currentExp, int totalExpForNextLevel) {
        return currentExp >= totalExpForNextLevel;
    }
}
