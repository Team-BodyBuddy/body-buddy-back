package BodyBuddy.demo.domain.rankingPoint.entity;

import java.util.ArrayList;
import java.util.List;

import BodyBuddy.demo.domain.member.entity.Member;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 랭킹 점수 엔티티 (RankingPoint)
 * 회원별 랭킹 데이터를 관리
 */
@Entity
@Table(name = "RankingPoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 랭킹 점수 ID

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member; // 회원 정보 (연결된 회원 엔티티)

	private Long activityScore;  // 운동량 점수
	private Long goalBonus;      // 목표 달성 보너스
	private Long intensityBonus; // 운동 강도 보너스

	private Long totalScore; // 총 점수 (자동 계산)

	@ElementCollection
	@CollectionTable(name = "unread_notifications", joinColumns = @JoinColumn(name = "ranking_point_id"))
	@Column(name = "message")
	private List<String> unreadNotifications = new ArrayList<>(); // 사용자에게 랭킹 보너스 메시지

	public void addNotification(String message) {
		this.unreadNotifications.add(message);
	}

	public void clearNotifications() {
		this.unreadNotifications.clear();
	}
}