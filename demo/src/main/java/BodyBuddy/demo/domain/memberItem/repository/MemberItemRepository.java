package BodyBuddy.demo.domain.memberItem.repository;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

  @Query("""
    SELECT DISTINCT mi FROM MemberItem mi
    LEFT JOIN FETCH mi.items i
    WHERE mi.avatar.id = :avatarId AND mi.isEquipped = true
""")
  List<MemberItem> findWearingItemsByAvatar(@Param("avatarId") Long avatarId);
}