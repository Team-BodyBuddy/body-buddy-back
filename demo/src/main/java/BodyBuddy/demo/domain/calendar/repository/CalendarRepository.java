package BodyBuddy.demo.domain.calendar.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.calendar.entity.Calendar;


@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
	Optional<Calendar> findByMemberIdAndDate(Long memberId, LocalDate date);
	List<Calendar> findByMemberIdAndDateBetween(Long memberId, LocalDate startDate, LocalDate endDate);
	List<Calendar> findByMemberId(Long memberId);
}