package BodyBuddy.demo.domain.trainer.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Trainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // 트레이너 이름

	@Column(nullable = false, unique = true)
	private String uuid;  // 추가적인 고유 값(랜덤 UUID)

	@Enumerated(EnumType.STRING)
	private Gender gender; // 성별

	@Column(nullable = false)
	private LocalDate birthday;

	private int badgeCount; // 뱃지 개수

	private String realName; // 실명
	private Float height; // 키 (cm)
	private Float weight; // 몸무게 (kg)

	@ManyToOne(optional = true) // 체육관 소속 정보 (nullable)
	@JoinColumn(name = "gym_id", nullable = true)
	private Gym gym;

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Member> members = new ArrayList<>();

	@PrePersist
	public void createUuid() {
		this.uuid = java.util.UUID.randomUUID().toString();
	}
}