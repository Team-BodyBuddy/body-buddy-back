package BodyBuddy.demo.domain.badge.entity;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Table(name = "Badge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_id")
	private Long id;

	@Column(nullable = false)
	private String badgeName;

	@Column(nullable = false)
	private String badgeDescription;

	private String iconUrl; //TODO 뱃지 아이콘 URL은 따로 분리해서 각 뱃지에 대한 아이콘의 URL을 DB에 저장해놓고 불러오는 식으로 추후에 구현
							// 현재는 테스트 URL을 사용.

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id", nullable = false)
	private Trainer trainer;

	public void assignTrainer(Trainer trainer) {
		this.trainer = trainer;
		trainer.getBadges().add(this);
	}

	public Badge(String badgeName, String badgeDescription, String iconUrl, Trainer trainer) {
		this.badgeName = badgeName;
		this.badgeDescription = badgeDescription;
		this.iconUrl = iconUrl;
		this.trainer = trainer;
	}
}
