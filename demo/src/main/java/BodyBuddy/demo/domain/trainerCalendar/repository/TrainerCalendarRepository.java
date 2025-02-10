package BodyBuddy.demo.domain.trainerCalendar.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.trainerCalendar.entity.TrainerCalendar;

@Repository
public interface TrainerCalendarRepository extends JpaRepository<TrainerCalendar, Long> {
	Optional<TrainerCalendar> findByTrainerIdAndMemberIdAndDate(Long trainerId, Long memberId, LocalDate date);

}