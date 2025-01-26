package BodyBuddy.demo.domain.dailyMemo.dto;

import BodyBuddy.demo.domain.dailyMemo.entity.DailyMemo;

public record DailyMemoResponse(
	Long memoId,
	String memo
) {
	public static DailyMemoResponse from(DailyMemo dailyMemo) {
		return new DailyMemoResponse(
			dailyMemo.getId(),
			dailyMemo.getMemo()
		);
	}
}
