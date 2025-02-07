package BodyBuddy.demo.domain.member.entity;

import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import BodyBuddy.demo.global.common.commonEnum.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.dailyEvaluation.entity.DailyEvaluation;
import BodyBuddy.demo.domain.routine.entity.Routine;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.inbody.entity.InBody;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

	@Enumerated(EnumType.STRING)
	@NotNull(message = "성별은 필수 입력 항목입니다.")
	private Gender gender; // 성별

	@Column(nullable = false)
	@NotNull(message = "생년월일은 필수 입력 항목입니다.")
	private LocalDate birthday;

	@Column(unique = true)
	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
	private String nickname; // 닉네임

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

	@Column(nullable = false)
	private long totalPoints = 0L;

	@NotNull(message = "지역은 필수 입력 항목입니다.")
	@Enumerated(EnumType.STRING)
	private Region region;

	@ManyToOne(optional = true) // 체육관 소속 정보
	@JoinColumn(name = "gym_id")
	private Gym gym;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;


	@JsonIgnore
	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private Avatar avatar;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InBody> inBodies = new ArrayList<>();


	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Calendar> calendars = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DailyEvaluation> dailyEvaluations = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Routine> routines = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrainerCalendar> trainerCalendars = new ArrayList<>();

	public void setPassword(String password) {
		this.password = password;
	}


}
