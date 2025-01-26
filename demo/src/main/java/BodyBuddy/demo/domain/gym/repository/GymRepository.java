package BodyBuddy.demo.domain.gym.repository;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.global.common.commonEnum.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym, Long> {
    List<Gym> findByRegion(Region region);
}
