package BodyBuddy.demo.domain.dailyevaluation.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import BodyBuddy.demo.domain.dailyevaluation.DailyEvaluation;

@Repository
public interface DailyEvaluationRepository extends JpaRepository<DailyEvaluation, Long> {

	@Query("SELECT d FROM DailyEvaluation d WHERE d.member.id = :memberId AND d.date = :date")
	Optional<DailyEvaluation> findByMemberIdAndDate(@Param("memberId") Long memberId, @Param("date") LocalDate date);
}
