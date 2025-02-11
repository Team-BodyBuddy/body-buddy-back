package BodyBuddy.demo.domain.routine.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.common.commonEnum.RoutineType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}