package BodyBuddy.demo.domain.calendar.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.calendar.dto.CalendarDayInfo;
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
public class Calendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	@JsonIgnore // JSON 직렬화에서 무시
	private Member member;

	private LocalDate date;

	@Enumerated(EnumType.STRING) // Enum을 문자열로 저장
	private EvaluationStatus evaluationStatus; // BAD,SOSO,GOOD

	@Enumerated(EnumType.STRING)
	private CalendarDayInfo.IndicatorType indicator = CalendarDayInfo.IndicatorType.NONE; // 점 상태 (NONE이 기본값)


}