package BodyBuddy.demo.domain.routine;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.member.Member;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Routine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@JsonIgnore
	private Member member;

	private String name;

	@Enumerated(EnumType.STRING)
	private RoutineType type; // "ROUTINE" or "CLASS"

	private LocalDate date;

	@Column(nullable = false)
	private Boolean completed = false;


}
