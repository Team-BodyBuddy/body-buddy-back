package BodyBuddy.demo.domain.routine.service;

import BodyBuddy.demo.domain.calendar.Calendar;
import BodyBuddy.demo.domain.calendar.CalendarMapper;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.routine.RoutineMapper;
import BodyBuddy.demo.domain.routine.dto.RoutineResponse;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.MemberRepository;
import BodyBuddy.demo.domain.routine.Routine;
import BodyBuddy.demo.domain.routine.dto.RoutineRequest;
import BodyBuddy.demo.domain.routine.repository.RoutineRepository;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CalendarService calendarService;

    /**
     * 루틴 추가 메서드
     */
    @Transactional
    public RoutineResponse addRoutine(RoutineRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원 ID입니다: " + request.getMemberId()));
        Routine routine = new Routine();
        routine.setMember(member);
        routine.setName(request.getName());
        routine.setType(request.getType());
        routine.setDate(request.getDate());
        routine.setCompleted(false);

        //캘린더 점  업데이트
        calendarService.updateIndicator(request.getMemberId(), request.getDate());

        Routine savedRoutine = routineRepository.save(routine);
        return RoutineMapper.toResponse(savedRoutine);


    }

    /**
     * 루틴 완료 처리 메서드
     */
    @Transactional
    public void completedRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(()
            -> new IllegalArgumentException("Invalid Routine ID"));
        routine.setCompleted(true);
        routineRepository.save(routine);
    }


    /**
     * 루틴 삭제 메서드
     */
    @Transactional
    public void deleteRoutine(Long routineId) {
        routineRepository.deleteById(routineId);
    }


    /**
     * 특정 회원의 특정 날짜 루틴 조회
     */
    @Transactional(readOnly = true)
    public List<RoutineResponse> getRoutinesByDate(Long memberId, LocalDate date) {
        List<Routine> routines = routineRepository.findByMemberIdAndDate(memberId, date);
        return routines.stream()
            .map(RoutineMapper::toResponse)
            .toList();
    }


}
