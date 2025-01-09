package BodyBuddy.demo.domain.trainerRequest.repository;

import BodyBuddy.demo.domain.trainerRequest.TrainerRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRequestRepository extends JpaRepository<TrainerRequest, Long> {
    List<TrainerRequest> findAllByTrainer_Id(Long trainerId);
}
