package BodyBuddy.demo.domain.dailyEvaluation.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DailyEvaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@JsonIgnore
	private Member member;

	private LocalDate date;

	@Enumerated(EnumType.STRING)
	private EvaluationStatus evaluation; //NONE,BAD,SOSO,GOOD
}