package BodyBuddy.demo.domain.avatar.service;

import org.springframework.stereotype.Service;

@Service
public class LevelUpService {

  /**
   * 특정 레벨에서 다음 레벨로 올라가기 위한 경험치를 계산
   */
  public static long calculateRequiredExp(long level) {
    if (level == 1) return 150; // 1레벨 → 2레벨 (150)
    if (level == 2) return 250; // 2레벨 → 3레벨 (250)
    return calculateRequiredExp(level - 1) + 50; // 이후는 +50씩 증가
  }

  /**
   * 현재 경험치를 확인하여 필요한 경우 자동으로 레벨업 수행
   */
  public static long checkLevelUp(long currentLevel, long currentExp) {
    while (currentExp >= calculateRequiredExp(currentLevel)) {
      currentLevel++;
    }
    return currentLevel;
  }
}