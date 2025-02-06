package BodyBuddy.demo.domain.trainer.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import BodyBuddy.demo.domain.badge.entity.Badge;
import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import BodyBuddy.demo.global.common.commonEnum.Region;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Trainer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "성별은 필수 입력 항목입니다.")
	private Gender gender; // 성별

	@Column(nullable = false)
	@NotNull(message = "생년월일은 필수 입력 항목입니다.")
	private LocalDate birthday;

	@Column(nullable = false)
	@NotBlank(message = "실명은 필수 입력 항목입니다.")
	private String realName; // 실명

	@Column(nullable = false, unique = true)
	@NotBlank(message = "ID는 필수 입력 항목입니다.")
	private String loginId; // 로그인용 ID

	@Column(nullable = false)
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	private String password;

	@NotNull(message = "키는 필수 입력 항목입니다.")
	private Float height; // 키 (cm)

	@NotNull(message = "몸무게는 필수 입력 항목입니다.")
	private Float weight; // 몸무게 (kg)

	@NotNull(message = "지역은 필수 입력 항목입니다.")
	@Enumerated(EnumType.STRING)
	private Region region;

	@Column(nullable = false, unique = true)
	private String uuid;  // 추가적인 고유 값(랜덤 UUID)

	@Column(nullable = true) // 프로필 사진 URL
	private String profileImageUrl;

	private int badgeCount; // 뱃지 개수

	@ManyToOne(optional = true) // 체육관 소속 정보 (nullable)
	@JoinColumn(name = "gym_id", nullable = true)
	private Gym gym;

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Member> members = new ArrayList<>();

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Badge> badges=new ArrayList<>();

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Portfolio> portfolios = new ArrayList<>();

	@Column(name = "refresh_token")
	private String refreshToken;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@PrePersist
	public void createUuid() {
		this.uuid = java.util.UUID.randomUUID().toString();
	}

	public void updateProfileImage(String newProfileImageUrl) {
		this.profileImageUrl = newProfileImageUrl;
	}
}