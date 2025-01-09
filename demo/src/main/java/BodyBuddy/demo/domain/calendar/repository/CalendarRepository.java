package BodyBuddy.demo.domain.calendar.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.calendar.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByMemberIdAndDate(Long memberId, LocalDate date);

    List<Calendar> findByMemberId(Long memberId);
}
