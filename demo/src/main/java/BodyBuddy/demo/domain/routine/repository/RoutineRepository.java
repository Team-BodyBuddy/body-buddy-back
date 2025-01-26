package BodyBuddy.demo.domain.routine.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.routine.entity.Routine;
import BodyBuddy.demo.global.common.commonEnum.RoutineType;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
	List<Routine> findByMemberIdAndDateAndType(Long memberId, LocalDate date, RoutineType type);
	List<Routine> findByMemberIdAndDate(Long memberId, LocalDate date);
}