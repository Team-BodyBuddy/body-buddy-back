package BodyBuddy.demo.domain.dailyEvalExpr.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.dailyEvalExpr.entity.DailyEvalExpr;
import BodyBuddy.demo.domain.dailyEvalExpr.repository.DailyEvalExprRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.global.apiPayload.code.error.AvatarErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyEvalExprService {

	private final DailyEvalExprRepository dailyEvalExprRepository;
	private final AvatarRepository avatarRepository;
	private final MemberRepository memberRepository;

	/**
	 * 오늘의 평가 연속 수행 일수 업데이트 및 아바타 점수 증가
	 */
	public void updateStreakAndAvatar(Member member, LocalDate today) {
		memberRepository.findById(member.getId())
			.orElseThrow(()-> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));
		DailyEvalExpr dailyEvalExpr = dailyEvalExprRepository.findByMemberId(member.getId())
			.orElseGet(() -> DailyEvalExpr.builder()
				.member(member)
				.currentStreak(0)
				.lastEvaluatedDate(null)
				.build());

		if (dailyEvalExpr.getLastEvaluatedDate() != null) {
			LocalDate yesterday = today.minusDays(1);
			if (dailyEvalExpr.getLastEvaluatedDate().isEqual(yesterday)) {
				dailyEvalExpr.incrementStreak();
			} else {
				dailyEvalExpr.resetStreak();
			}
		} else {
			dailyEvalExpr.resetStreak();
		}

		dailyEvalExpr.updateLastEvaluatedDate(today);
		dailyEvalExprRepository.save(dailyEvalExpr);

		// 아바타 점수 업데이트
		Avatar avatar = member.getAvatar();
		if (avatar == null) {
			throw new BodyBuddyException(AvatarErrorCode.AVATAR_NOT_FOUND);
		}

		int streak = dailyEvalExpr.getCurrentStreak();
		long pointsToAdd = calculatePoints(streak);

		avatar.addExp(pointsToAdd);
		avatar.addPoint(pointsToAdd);
		avatar.addRankingScore(pointsToAdd);
		avatarRepository.save(avatar);
	}

	/**
	 * 연속 일수에 따른 점수 계산
	 * (해당 내용은 수정할 겁니다.)
	 */
	private long calculatePoints(int streak) {
		if (streak < 7) {
			return 10;
		} else if (streak < 14) {
			return 20;
		} else if (streak < 21) {
			return 30;
		} else if (streak < 31) {
			return 50;
		} else {
			return 50; // 31일 이상도 50점으로 유지
		}
	}
}
