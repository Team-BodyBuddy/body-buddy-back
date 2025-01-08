package BodyBuddy.demo.domain.trainer.repository;


import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}