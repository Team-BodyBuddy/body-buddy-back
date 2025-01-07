package BodyBuddy.demo.domain.avatar.repository;

import BodyBuddy.demo.domain.avatar.domain.Avatar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository {
  Optional<Avatar> findByMemberId(Long memberId);
}
