package BodyBuddy.demo.domain.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.dailyEvaluation.entity.DailyEvaluation;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.domain.point.entity.Point;
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
import jakarta.persistence.Table;
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
	private Gender gender; // 성별

	@Column(nullable = false)
	private LocalDate birthday;

	private String nickname; // 닉네임
	private String realName; // 실명
	private Long level; // 회원 레벨
	private Float height; // 키 (cm)
	private Float weight; // 몸무게 (kg)

	@Column(nullable = false)
	private long totalPoints = 0L;

	@ManyToOne(optional = true) // 체육관 소속 정보
	@JoinColumn(name = "gym_id")
	private Gym gym;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Point> points = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberItem> items = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Avatar> avatars = new ArrayList<>();

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


}
