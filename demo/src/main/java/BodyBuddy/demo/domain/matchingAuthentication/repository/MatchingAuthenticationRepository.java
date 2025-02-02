package BodyBuddy.demo.domain.matchingAuthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import BodyBuddy.demo.domain.matchingAuthentication.entity.MatchingAuthentication;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.common.commonEnum.AuthenticationRequest;

public interface MatchingAuthenticationRepository extends JpaRepository<MatchingAuthentication, Long> {
	List<MatchingAuthentication> findByTrainerAndStatus(Trainer trainer, AuthenticationRequest status);

	long countByTrainerAndStatus(Trainer trainer, AuthenticationRequest status);
}
import org.springframework.data.jpa.repository.JpaRepository;

import BodyBuddy.demo.domain.matchingAuthentication.entity.MatchingAuthentication;

public interface MatchingAuthenticationRepository extends JpaRepository<MatchingAuthentication, Long> {
}
