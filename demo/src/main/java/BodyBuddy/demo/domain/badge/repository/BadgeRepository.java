package BodyBuddy.demo.domain.badge.repository;

import BodyBuddy.demo.domain.badge.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
