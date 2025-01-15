package BodyBuddy.demo.domain.avatar.repository;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
  Optional<Avatar> findByMemberId(Long memberId);
}
