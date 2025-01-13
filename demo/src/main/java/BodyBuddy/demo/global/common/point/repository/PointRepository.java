package BodyBuddy.demo.global.common.point.repository;

import BodyBuddy.demo.global.common.point.entity.Point;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByMemberId(Long memberId);
}
