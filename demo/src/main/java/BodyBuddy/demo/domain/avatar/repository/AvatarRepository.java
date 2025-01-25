package BodyBuddy.demo.domain.avatar.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BodyBuddy.demo.domain.avatar.entity.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}