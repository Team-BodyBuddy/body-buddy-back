package BodyBuddy.demo.domain.dailyEvalExpr.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.dailyEvalExpr.entity.DailyEvalExpr;

public record DailyEvalExprResponseDto(
	Long id,
	Long memberId,
	int currentStreak,
	LocalDate lastEvaluatedDate,
	Long avatarExp,
	Long avatarPoint,
	Long avatarRankingScore
) {
	public static DailyEvalExprResponseDto from(DailyEvalExpr dailyEvalExpr) {
		return new DailyEvalExprResponseDto(
			dailyEvalExpr.getId(),
			dailyEvalExpr.getMember().getId(),
			dailyEvalExpr.getCurrentStreak(),
			dailyEvalExpr.getLastEvaluatedDate(),
			dailyEvalExpr.getMember().getAvatar().getExp(),
			dailyEvalExpr.getMember().getAvatar().getPoint(),
			dailyEvalExpr.getMember().getAvatar().getRankingScore()
		);
	}
}
