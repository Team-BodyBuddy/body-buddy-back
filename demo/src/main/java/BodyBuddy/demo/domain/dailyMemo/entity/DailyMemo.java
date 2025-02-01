package BodyBuddy.demo.domain.dailyMemo.entity;

import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;
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
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMemo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String memo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_calendar_id", nullable = false)
	private TrainerCalendar trainerCalendar;

	public void assignCalendar(TrainerCalendar trainerCalendar) {
		this.trainerCalendar = trainerCalendar;
	}

	public void updateMemo(String newMemo) {
		this.memo = newMemo;
	}
}