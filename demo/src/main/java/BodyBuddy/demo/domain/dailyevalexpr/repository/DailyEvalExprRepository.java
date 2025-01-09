package BodyBuddy.demo.domain.dailyevalexpr.repository;

import BodyBuddy.demo.domain.dailyevalexpr.DailyEvalExpr;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEvalExprRepository extends JpaRepository<DailyEvalExpr, Long> {

    Optional<DailyEvalExpr> findByMemberId(Long memberId);
}
