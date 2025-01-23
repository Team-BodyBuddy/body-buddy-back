package BodyBuddy.demo.domain.gym.entity;

import java.util.ArrayList;
import java.util.List;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.common.commonEnum.Region;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gym {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 체육관 고유 ID

	private String name;    // 체육관 이름

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Region region; // 지역

	@OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Trainer> trainers= new ArrayList<>();; // 해당 체육관의 트레이너 리스트
}
