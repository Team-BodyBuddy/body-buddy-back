package BodyBuddy.demo.domain.avatarSkin.repository;

import BodyBuddy.demo.domain.avatar.domain.Avatar;
import BodyBuddy.demo.domain.avatarSkin.domain.AvatarSkin;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarSkinRepository extends JpaRepository {
  List<AvatarSkin> findByAvatarId(Long avatarId);
}
