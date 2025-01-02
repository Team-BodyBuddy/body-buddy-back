package BodyBuddy.demo.global.common.entity;

/**
 * 활동 유형 (ActivityType) 열거형
 * 활동 유형에 따라 경험치를 차등 지급
 */
public enum ActivityType {
    GOAL,          // 목표 기반 경험치
    ACCUMULATION,  // 누적 성과 기반 경험치
    DIFFICULTY,    // 난이도 기반 경험치
    COMPETITION    // 경쟁 기반 경험치
}
