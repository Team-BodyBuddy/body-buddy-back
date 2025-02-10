package BodyBuddy.demo.domain.trainerCalendar.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.classSchedule.entity.ClassSchedule;
import BodyBuddy.demo.domain.classSchedule.repository.ClassScheduleRepository;
import BodyBuddy.demo.domain.dailyMemo.entity.DailyMemo;
import BodyBuddy.demo.domain.dailyMemo.repository.DailyMemoRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;
import BodyBuddy.demo.domain.trainerCalendar.repository.TrainerCalendarRepository;
import BodyBuddy.demo.global.apiPayload.code.error.CalendarErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerCalendarService {
	private final TrainerCalendarRepository trainerCalendarRepository;
	private final ClassScheduleRepository classScheduleRepository;
	private final DailyMemoRepository dailyMemoRepository;
	private final TrainerRepository trainerRepository;
	private final MemberRepository memberRepository;

	/**
	 * 특정 트레이너, 회원의 특정 날짜의 캘린더 조회
	 */
	@Transactional
	public TrainerCalendar getOrCreateCalendarByDate(Long trainerId, Long memberId, LocalDate date) {
		Trainer trainer = trainerRepository.findById(trainerId)
			.orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));

		// 캘린더 조회 또는 생성
		return trainerCalendarRepository.findByTrainerIdAndMemberIdAndDate(trainerId, memberId, date)
			.orElseGet(() -> {
				TrainerCalendar newCalendar = TrainerCalendar.builder()
					.trainer(trainer)
					.member(member)
					.date(date)
					.hasClass(false)
					.build();
				return trainerCalendarRepository.save(newCalendar);
			});
	}

	/**
	 * 특정 캘린더(calendarId)에 새로운 수업을 추가
	 */
	public ClassSchedule addClassSchedule(Long calendarId, String description) {
		TrainerCalendar calendar = trainerCalendarRepository.findById(calendarId)
			.orElseThrow(() -> new BodyBuddyException(CalendarErrorCode.CALENDAR_NOT_FOUND));
		ClassSchedule schedule = ClassSchedule.builder()
			.description(description)
			.completed(false)
			.trainerCalendar(calendar)
			.build();
		classScheduleRepository.save(schedule);

		calendar.updateHasClass();
		trainerCalendarRepository.save(calendar);

		return schedule;
	}

	/**
	 *  특정 수업(classScheduleId)을 삭제합니다.
	 */
	public void removeClassSchedule(Long classScheduleId) {
		ClassSchedule classSchedule = classScheduleRepository.findById(classScheduleId)
			.orElseThrow(() -> new BodyBuddyException(CalendarErrorCode.CLASS_SCHEDULE_NOT_FOUND));

		TrainerCalendar calendar = classSchedule.getTrainerCalendar();
		classScheduleRepository.delete(classSchedule);

		// hasClass 업데이트
		calendar.updateHasClass();
		trainerCalendarRepository.save(calendar);
	}

	/**
	 * 특정 수업의 완료 여부를 토글합니다.
	 */
	public ClassSchedule toggleClassCompletion(Long classScheduleId) {
		ClassSchedule classSchedule = classScheduleRepository.findById(classScheduleId)
			.orElseThrow(() ->  new BodyBuddyException(CalendarErrorCode.CLASS_SCHEDULE_NOT_FOUND));
		classSchedule.toggleCompleted();
		return classSchedule;
	}

	/**
	 * 특정 캘린더(calendarId)에 연결된 메모를 추가하거나 업데이트
	 */
	public DailyMemo updateDailyMemo(Long calendarId, String memo) {
		TrainerCalendar calendar = trainerCalendarRepository.findById(calendarId)
			.orElseThrow(() -> new BodyBuddyException(CalendarErrorCode.CALENDAR_NOT_FOUND));
		DailyMemo dailyMemo = calendar.getDailyMemo();
		if (dailyMemo == null) {
			dailyMemo = DailyMemo.builder().memo(memo).trainerCalendar(calendar).build();
			dailyMemoRepository.save(dailyMemo);
			calendar.setDailyMemo(dailyMemo);
		} else {
			dailyMemo.updateMemo(memo);
		}
		return dailyMemo;
	}

}
