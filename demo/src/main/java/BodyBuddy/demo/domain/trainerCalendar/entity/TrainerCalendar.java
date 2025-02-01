package BodyBuddy.demo.domain.trainerCalendar.entity;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import BodyBuddy.demo.domain.classSchedule.entity.ClassSchedule;
import BodyBuddy.demo.domain.dailyMemo.entity.DailyMemo;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class TrainerCalendar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "trainer_id", nullable = false)
	private Trainer trainer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	private LocalDate date; // 날짜

	@OneToMany(mappedBy = "trainerCalendar", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClassSchedule> classSchedules = new ArrayList<>();

	@OneToOne(mappedBy = "trainerCalendar", cascade = CascadeType.ALL, orphanRemoval = true)
	private DailyMemo dailyMemo;

	// 주황색 점 여부를 나타내는 필드
	private boolean hasClass = false;

	public void setDailyMemo(DailyMemo dailyMemo) {
		this.dailyMemo = dailyMemo;
		dailyMemo.assignCalendar(this);
	}

	public void updateHasClass() {
		this.hasClass = !classSchedules.isEmpty(); // 수업이 하나라도 있으면 true
	}
}
