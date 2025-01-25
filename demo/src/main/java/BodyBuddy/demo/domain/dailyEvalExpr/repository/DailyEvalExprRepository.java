package BodyBuddy.demo.domain.dailyEvalExpr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.dailyEvalExpr.entity.DailyEvalExpr;

@Repository
public interface DailyEvalExprRepository extends JpaRepository<DailyEvalExpr, Long> {
	Optional<DailyEvalExpr> findByMemberId(Long memberId);
}