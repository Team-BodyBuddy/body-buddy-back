package BodyBuddy.demo.domain.matchingAuthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import BodyBuddy.demo.domain.matchingAuthentication.entity.MatchingAuthentication;

public interface MatchingAuthenticationRepository extends JpaRepository<MatchingAuthentication, Long> {
}
