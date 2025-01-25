package BodyBuddy.demo.domain.dailyEvaluation.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.dailyEvaluation.entity.DailyEvaluation;

@Repository
public interface DailyEvaluationRepository extends JpaRepository<DailyEvaluation, Long> {
	Optional<DailyEvaluation> findByMemberIdAndDate(Long memberId, LocalDate date);
}