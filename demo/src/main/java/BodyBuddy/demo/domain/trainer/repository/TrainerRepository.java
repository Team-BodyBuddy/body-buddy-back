package BodyBuddy.demo.domain.trainer.repository;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
