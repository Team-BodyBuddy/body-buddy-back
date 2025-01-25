package BodyBuddy.demo.domain.dailyEvalExpr.entity;

import java.time.LocalDate;

import BodyBuddy.demo.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DailyEvalExpr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, unique = true)
	private Member member;

	private int currentStreak; // 연속 수행 일수

	private LocalDate lastEvaluatedDate; // 마지막 평가 날짜


	public void incrementStreak() {
		this.currentStreak += 1;
	}


	public void resetStreak() {
		this.currentStreak = 1;
	}


	public void updateLastEvaluatedDate(LocalDate date) {
		this.lastEvaluatedDate = date;
	}
}