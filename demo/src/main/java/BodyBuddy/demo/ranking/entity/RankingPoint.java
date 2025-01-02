package BodyBuddy.demo.ranking.entity;

import BodyBuddy.demo.global.common.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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

    private int activityScore;  // 운동량 점수
    private int goalBonus;      // 목표 달성 보너스
    private int intensityBonus; // 운동 강도 보너스

    private int totalScore; // 총 점수 (자동 계산)
}
