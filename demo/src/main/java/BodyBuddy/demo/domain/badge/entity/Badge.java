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
	private String name; // Badge name

	private String iconUrl; // URL for badge icon (optional)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainer_id", nullable = false)
	private Trainer trainer;

}
