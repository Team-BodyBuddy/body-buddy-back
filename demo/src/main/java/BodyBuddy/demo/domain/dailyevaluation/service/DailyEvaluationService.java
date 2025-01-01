package BodyBuddy.demo.domain.dailyevaluation.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BodyBuddy.demo.domain.calendar.repository.CalendarRepository;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.dailyevaluation.DailyEvaluation;

import BodyBuddy.demo.domain.dailyevaluation.dto.EvaluationRequest;
import BodyBuddy.demo.domain.dailyevaluation.repository.DailyEvaluationRepository;
import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.MemberRepository;
import jakarta.transaction.Transactional;

@Service
public class DailyEvaluationService {

	@Autowired
	private DailyEvaluationRepository dailyEvaluationRepository;

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private MemberRepository memberRepository;
	/**
	 * 하루 평가 저장 메서드
	 */
	@Transactional
	public DailyEvaluation saveDailyEvaluation(EvaluationRequest request) {
		Member member = memberRepository.findById(request.getMemberId())
			.orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

		DailyEvaluation dailyEvaluation = new DailyEvaluation();
		dailyEvaluation.setMember(member);
		dailyEvaluation.setDate(request.getDate());
		dailyEvaluation.setEvaluation(request.getEvaluationStatus());

		// DailyEvaluation 저장
		dailyEvaluation = dailyEvaluationRepository.save(dailyEvaluation);

		// Calendar 상태 업데이트
		calendarService.updateCalendarEvaluation(
			request.getMemberId(),
			request.getDate(),
			request.getEvaluationStatus()
		);

		return dailyEvaluation;
	}



}
