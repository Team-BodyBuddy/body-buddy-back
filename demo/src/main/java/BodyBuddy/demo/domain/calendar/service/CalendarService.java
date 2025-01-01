package BodyBuddy.demo.domain.calendar.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.EvaluationStatus;
import BodyBuddy.demo.domain.calendar.Calendar;
import BodyBuddy.demo.domain.calendar.dto.CalendarDayInfo;
import BodyBuddy.demo.domain.calendar.dto.CalendarRequest;
import BodyBuddy.demo.domain.calendar.repository.CalendarRepository;
import BodyBuddy.demo.domain.dailyevaluation.DailyEvaluation;
import BodyBuddy.demo.domain.dailyevaluation.repository.DailyEvaluationRepository;
import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.member.MemberRepository;
import BodyBuddy.demo.domain.routine.RoutineType;
import BodyBuddy.demo.domain.routine.repository.RoutineRepository;
import BodyBuddy.demo.exception.CalendarNotFoundException;


@Service
public class CalendarService {

	@Autowired
	private CalendarRepository calendarRepository;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private DailyEvaluationRepository dailyEvaluationRepository;

	@Autowired
	private MemberRepository memberRepository;

	/**
	 * 캘린더 생성 또는 업데이트
	 */
	@Transactional
	public Calendar createOrUpdateCalendar(CalendarRequest request) {
		Member member = memberRepository.findById(request.getMemberId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID입니다: " + request.getMemberId()));
		Calendar calendar = calendarRepository.findByMemberIdAndDate(request.getMemberId(), request.getDate())
			.orElse(new Calendar());
		calendar.setMember(member);
		calendar.setDate(request.getDate());
		calendar.setEvaluationStatus(request.getEvaluationStatus());
		return calendarRepository.save(calendar);
	}

	/**
	 * 평가 상태 업데이트
	 */
	@Transactional
	public Calendar updateCalendarEvaluation(Long memberId, LocalDate date, EvaluationStatus evaluationStatus) {
		Calendar calendar = calendarRepository.findByMemberIdAndDate(memberId, date)
			.orElse(new Calendar());
		Member member = new Member();
		member.setId(memberId);
		calendar.setMember(member);
		calendar.setDate(date);
		calendar.setEvaluationStatus(evaluationStatus);
		return calendarRepository.save(calendar);
	}

	/**
	 * 특정 회원의 캘린더 조회
	 */
	@Transactional(readOnly = true)
	public List<Calendar> getCalendarByMemberId(Long memberId) {
		return calendarRepository.findByMemberId(memberId);

	}

	/**
	 * 특정 회원의 한 달치 캘린더 데이터 조회
	 */
	public Map<LocalDate, CalendarDayInfo> getMonthlyCalendarData(Long memberId, LocalDate startDate, LocalDate endDate) {
		Map<LocalDate, CalendarDayInfo> calendarData = new HashMap<>();
		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			List<Object[]> counts = routineRepository.findTypeCountsByMemberAndDate(memberId, date);
			CalendarDayInfo.IndicatorType indicator = determineIndicator(counts);

			Optional<DailyEvaluation> evaluation = dailyEvaluationRepository.findByMemberIdAndDate(memberId, date);
			EvaluationStatus evaluationStatus = evaluation.map(DailyEvaluation::getEvaluation).orElse(null);

			calendarData.put(date, new CalendarDayInfo(indicator, evaluationStatus));
		}
		return calendarData;
	}

	/**
	 * 루틴 및 클래스 상태로 Indicator 결정
	 */
	private CalendarDayInfo.IndicatorType determineIndicator(List<Object[]> counts) {
		boolean hasRoutine = false;
		boolean hasClass = false;

		for (Object[] count : counts) {
			RoutineType type = (RoutineType) count[0];
			Long countValue = (Long) count[1];

			if (type == RoutineType.ROUTINE && countValue > 0) {
				hasRoutine = true;
			}
			if (type == RoutineType.CLASS && countValue > 0) {
				hasClass = true;
			}
		}

		if (hasRoutine && hasClass) {
			return CalendarDayInfo.IndicatorType.BOTH; // Enum 사용
		} else if (hasRoutine) {
			return CalendarDayInfo.IndicatorType.BLUE; // Enum 사용
		} else if (hasClass) {
			return CalendarDayInfo.IndicatorType.RED; // Enum 사용
		} else {
			return CalendarDayInfo.IndicatorType.NONE; // Enum 사용
		}
	}
}
