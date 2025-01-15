package BodyBuddy.demo.domain.region.repository;

import BodyBuddy.demo.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
