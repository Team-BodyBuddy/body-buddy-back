package BodyBuddy.demo.domain.dailyEvaluation.service;

import java.time.LocalDate;

import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.calendar.repository.CalendarRepository;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationRequestDto;
import BodyBuddy.demo.domain.dailyEvaluation.dto.DailyEvaluationResponseDto;
import BodyBuddy.demo.domain.dailyEvaluation.entity.DailyEvaluation;
import BodyBuddy.demo.domain.dailyEvaluation.repository.DailyEvaluationRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.commonEnum.EvaluationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyEvaluationService {

	private final CalendarService calendarService;
	private final CalendarRepository calendarRepository;
	private final DailyEvaluationRepository dailyEvaluationRepository;
	private final MemberRepository memberRepository;

	/**
	 * 오늘의 평가 상태 설정
	 */
	public CalendarResponse setDailyEvaluation(DailyEvaluationRequestDto dto) {
		Member member = memberRepository.findById(dto.memberId())
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		// Calendar 조회 또는 생성
		Calendar calendar = calendarService.getOrCreateCalendar(member, dto.date());

		// DailyEvaluation 조회 또는 생성
		DailyEvaluation evaluation = dailyEvaluationRepository.findByMemberIdAndDate(member.getId(), dto.date())
			.orElseGet(() -> DailyEvaluation.createDailyEvaluation(calendar, member, dto.date(), EvaluationStatus.NONE));

		// 평가 상태 변경
		evaluation.changeEvaluationStatus(dto.status());

		// 연관관계 설정
		calendar.setDailyEvaluation(evaluation);

		// 저장
		dailyEvaluationRepository.save(evaluation);
		calendarRepository.save(calendar);

		return CalendarResponse.from(calendar);
	}

	/**
	 * 특정 회원의 특정 날짜의 오늘의 평가 조회
	 */
	@Transactional(readOnly = true)
	public DailyEvaluationResponseDto getDailyEvaluation(Long memberId, LocalDate date) {
		memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		DailyEvaluation evaluation = dailyEvaluationRepository.findByMemberIdAndDate(memberId, date)
			.orElseThrow(() -> new IllegalArgumentException("해당 날짜에 대한 평가가 존재하지 않습니다."));

		return DailyEvaluationResponseDto.from(evaluation);
	}
}
