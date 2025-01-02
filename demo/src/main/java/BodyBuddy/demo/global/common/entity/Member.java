package BodyBuddy.demo.global.common.entity;

import BodyBuddy.demo.gym.entity.Gym;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원(Member) 엔티티
 * 회원의 기본 정보, 레벨, 경험치, 소속 체육관(Gym) 등 관리
 */
@Entity
@Table(name = "Member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 고유 ID

    private int gender; // 성별 (0: 여성, 1: 남성)
    private String nickname; // 닉네임
    private String realName; // 실명
    private int level; // 회원 레벨
    private int experience; // 누적 경험치
    private float height; // 키 (cm)
    private float weight; // 몸무게 (kg)

    private int streakDays; // 연속 운동 일수

    @ManyToOne(optional = true) // 체육관 소속 정보 (nullable)
    @JoinColumn(name = "gym_id", nullable = true)
    private Gym gym;

    /**
     * 경험치 추가 메서드
     * @param exp 추가할 경험치
     */
    public void addExperience(int exp) {
        this.experience += exp;
    }

    /**
     * 레벨업 메서드
     * 레벨 상승 후 경험치를 초기화
     */
    public void levelUp() {
        this.level++;
        this.experience = 0; // 레벨업 시 경험치 초기화
    }
}
