package BodyBuddy.demo.domain.dailyevaluation.service;

import BodyBuddy.demo.domain.dailyevaluation.DailyEvaluationMapper;
import BodyBuddy.demo.domain.dailyevaluation.dto.DailyEvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DailyEvaluationResponse saveDailyEvaluation(EvaluationRequest request) {
        // 회원 정보 조회
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // DailyEvaluation 생성 및 저장
        DailyEvaluation dailyEvaluation = DailyEvaluation.builder()
            .member(member)
            .date(request.getDate())
            .evaluation(request.getEvaluationStatus())
            .build();

        DailyEvaluation savedDailyEvaluation = dailyEvaluationRepository.save(dailyEvaluation);

        // Calendar 상태 업데이트
        calendarService.updateCalendarEvaluation(
            request.getMemberId(),
            request.getDate(),
            request.getEvaluationStatus()
        );

        // 저장된 DailyEvaluation을 응답 DTO로 변환
        return DailyEvaluationMapper.toResponse(savedDailyEvaluation);
    }


}
