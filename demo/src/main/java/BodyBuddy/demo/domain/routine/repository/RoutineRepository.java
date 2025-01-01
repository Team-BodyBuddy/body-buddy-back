package BodyBuddy.demo.domain.routine.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.routine.Routine;
import BodyBuddy.demo.domain.routine.RoutineType;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {

	List<Routine> findByMemberIdAndDate(Long memberId, LocalDate date);
	Long countByMemberIdAndDate(Long memberId, LocalDate date);
	Long countByMemberIdAndTypeAndDate(Long memberId, RoutineType type, LocalDate date);

	@Query("SELECT r.type, COUNT(r) FROM Routine r WHERE r.member.id = :memberId AND r.date = :date GROUP BY r.type")
	List<Object[]> findTypeCountsByMemberAndDate(Long memberId, LocalDate date);

}
