package BodyBuddy.demo.trainer.repository;

import BodyBuddy.demo.trainer.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {}
