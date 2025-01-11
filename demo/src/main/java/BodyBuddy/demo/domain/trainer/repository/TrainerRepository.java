package BodyBuddy.demo.domain.trainer.repository;

import BodyBuddy.demo.domain.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t WHERE t.gym.region.id = :regionId AND t.gym.id = :gymId ORDER BY t.name ASC")
    List<Trainer> findByRegionAndGym(@Param("regionId") Long regionId, @Param("gymId") Long gymId);
}
