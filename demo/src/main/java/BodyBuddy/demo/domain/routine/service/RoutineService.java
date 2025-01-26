package BodyBuddy.demo.domain.routine.service;
import BodyBuddy.demo.domain.calendar.dto.CalendarDayInfo;
import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.calendar.service.CalendarService;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.routine.dto.RoutineRequestDto;
import BodyBuddy.demo.domain.routine.dto.RoutineResponseDto;
import BodyBuddy.demo.domain.routine.entity.Routine;
import BodyBuddy.demo.domain.routine.repository.RoutineRepository;
import BodyBuddy.demo.global.common.commonEnum.RoutineType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {

	private final RoutineRepository routineRepository;
	private final CalendarService calendarService;
	private final MemberRepository memberRepository;

	/**
	 * 루틴 또는 수업 추가
	 */
	public CalendarResponse addRoutine(RoutineRequestDto dto) {
		// 1. 회원 조회
		Member member = memberRepository.findById(dto.memberId())
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		// 2. 루틴 생성 및 저장
		Routine routine = Routine.builder()
			.member(member)
			.date(dto.date())
			.type(dto.routineType())
			.name(dto.name())
			.completed(false)
			.build();
		routineRepository.save(routine);

		// 3. Calendar 조회 또는 생성
		Calendar calendar = calendarService.getOrCreateCalendar(member, dto.date());

		// 4. 현재 루틴들을 조회하여 Indicator 계산
		List<RoutineType> routineTypes = routineRepository.findByMemberIdAndDate(member.getId(), dto.date())
			.stream()
			.map(Routine::getType)
			.toList();

		CalendarDayInfo.IndicatorType newIndicator = calendarService.calculateIndicators(routineTypes);
		calendar.changeIndicator(newIndicator);

		// 5. Indicator 업데이트 및 저장
		calendarService.updateCalendarIndicator(calendar, newIndicator);

		return CalendarResponse.from(calendar);
	}

	/**
	 * 루틴 또는 수업 삭제
	 */
	public CalendarResponse removeRoutine(RoutineRequestDto dto) {
		// 1. 회원 조회
		Member member = memberRepository.findById(dto.memberId())
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		// 2. 해당 루틴 조회
		List<Routine> routines = routineRepository.findByMemberIdAndDateAndType(member.getId(), dto.date(), dto.routineType());

		if (routines.isEmpty()) {
			throw new IllegalArgumentException("해당 루틴/수업이 존재하지 않습니다.");
		}

		// 3. 루틴 삭제
		routineRepository.deleteAll(routines);

		// 4. Calendar 조회
		Calendar calendar = calendarService.getOrCreateCalendar(member, dto.date());

		// 5. 남아있는 루틴들을 바탕으로 Indicator 재계산
		List<RoutineType> remainingRoutineTypes = routineRepository.findByMemberIdAndDate(member.getId(), dto.date())
			.stream()
			.map(Routine::getType)
			.toList();

		CalendarDayInfo.IndicatorType recalculated = calendarService.calculateIndicators(remainingRoutineTypes);
		calendar.changeIndicator(recalculated);

		// 6. Indicator 업데이트 및 저장
		calendarService.updateCalendarIndicator(calendar, recalculated);

		return CalendarResponse.from(calendar);
	}

	/**
	 * 특정 회원의 특정 날짜의 루틴/수업 조회
	 */
	@Transactional(readOnly = true)
	public List<RoutineResponseDto> getRoutines(Long memberId, java.time.LocalDate date) {
		memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		List<Routine> routines = routineRepository.findByMemberIdAndDate(memberId, date);
		return routines.stream()
			.map(RoutineResponseDto::from)
			.toList();
	}


	/**
	 * 루틴/수업의 수행 여부 토글
	 */
	public RoutineResponseDto toggleRoutineCompletion(Long routineId) {
		Routine routine = routineRepository.findById(routineId)
			.orElseThrow(() -> new IllegalArgumentException("해당 루틴/수업이 존재하지 않습니다."));

		// 수행 여부 토글
		routine.setCompleted(!routine.getCompleted());
		routineRepository.save(routine);

		return RoutineResponseDto.from(routine);
	}


}
