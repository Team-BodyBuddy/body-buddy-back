package BodyBuddy.demo.domain.gym.repository;

import BodyBuddy.demo.domain.gym.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {}
