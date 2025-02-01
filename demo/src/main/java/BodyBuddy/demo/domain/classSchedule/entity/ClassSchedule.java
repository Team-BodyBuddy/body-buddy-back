package BodyBuddy.demo.domain.classSchedule.entity;

import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description; // 수업 내용

	private boolean completed; // 수업 완료 여부

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_calendar_id", nullable = false)
	private TrainerCalendar trainerCalendar;

	public void toggleCompleted() {
		this.completed = !this.completed;
	}
}
