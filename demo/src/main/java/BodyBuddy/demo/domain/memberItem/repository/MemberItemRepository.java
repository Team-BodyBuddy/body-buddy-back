package BodyBuddy.demo.domain.memberItem.repository;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

  // 멤버가 착용한 아이템 조회 (선택 한 것)
  @Query("""
    SELECT DISTINCT mi FROM MemberItem mi
    LEFT JOIN FETCH mi.items i
    WHERE mi.avatar.id = :avatarId AND mi.isEquipped = true
""")
  List<MemberItem> findWearingItemsByAvatar(@Param("avatarId") Long avatarId);

  // 특정 멤버가 구매한 모든 아이템 조회
  List<MemberItem> findByAvatarMemberId(Long memberId);
}