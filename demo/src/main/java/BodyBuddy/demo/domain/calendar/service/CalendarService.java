package BodyBuddy.demo.domain.calendar.service;



import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.calendar.dto.CalendarDayInfo;
import BodyBuddy.demo.domain.calendar.dto.CalendarResponse;
import BodyBuddy.demo.domain.calendar.entity.Calendar;
import BodyBuddy.demo.domain.calendar.repository.CalendarRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.commonEnum.RoutineType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {

	private final CalendarRepository calendarRepository;
	private final MemberRepository memberRepository;

	/**
	 * 특정 회원의 전체 달력 조회
	 */
	@Transactional(readOnly = true)
	public List<CalendarResponse> getCalendar(Long memberId) {

		Member member=memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		List<Calendar> calendars = calendarRepository.findByMemberId(memberId);
		return calendars.stream()
			.map(CalendarResponse::from)
			.collect(Collectors.toList());
	}

	/**
	 * 특정 달의 캘린더 데이터 조회
	 */
	public List<CalendarResponse> getCalendarByMonth(Long memberId, YearMonth yearMonth) {
		Member member=memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		LocalDate startDate = yearMonth.atDay(1);
		LocalDate endDate = yearMonth.atEndOfMonth();

		List<Calendar> calendars = calendarRepository.findByMemberIdAndDateBetween(memberId, startDate, endDate);

		return calendars.stream()
			.map(CalendarResponse::from)
			.collect(Collectors.toList());
	}



	/**
	 * 캘린더의 Indicator 변경
	 * RoutineService에서 호출
	 */
	public void updateCalendarIndicator(Calendar calendar, CalendarDayInfo.IndicatorType newIndicator) {
		calendar.changeIndicator(newIndicator);
		calendarRepository.save(calendar);
	}

	/**
	 * 캘린더 조회 또는 생성
	 */
	public Calendar getOrCreateCalendar(Member member, LocalDate date) {
		return calendarRepository.findByMemberIdAndDate(member.getId(), date)
			.orElseGet(() -> Calendar.createCalendar(member, date));
	}

	/**
	 * Indicator 계산 로직 (Routine 추가/삭제 시 호출)
	 */
	public CalendarDayInfo.IndicatorType calculateIndicators(List<RoutineType> routineTypes) {
		boolean hasClass = routineTypes.contains(RoutineType.CLASS);
		boolean hasRoutine = routineTypes.contains(RoutineType.ROUTINE);

		if (hasClass && hasRoutine) {
			return CalendarDayInfo.IndicatorType.BOTH;
		} else if (hasClass) {
			return CalendarDayInfo.IndicatorType.RED;
		} else if (hasRoutine) {
			return CalendarDayInfo.IndicatorType.BLUE;
		} else {
			return CalendarDayInfo.IndicatorType.NONE;
		}
	}


}
