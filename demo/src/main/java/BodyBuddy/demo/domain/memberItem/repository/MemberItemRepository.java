package BodyBuddy.demo.domain.memberItem.repository;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

  // 멤버가 착용한 아이템 조회 (isEquipped = true 인 것만)
  @Query("""
    SELECT mi FROM MemberItem mi
    JOIN FETCH mi.item i
    WHERE mi.avatar.id = :avatarId AND mi.isEquipped = true
  """)
  List<MemberItem> findWearingItemsByAvatar(@Param("avatarId") Long avatarId);

  // 특정 멤버가 구매한 모든 아이템 조회
  @Query("""
    SELECT mi FROM MemberItem mi
    JOIN FETCH mi.item i
    WHERE mi.avatar.member.id = :memberId
  """)
  List<MemberItem> findByAvatarMemberId(@Param("memberId") Long memberId);

  // 특정 멤버가 특정 아이템을 이미 구매했는지 확인
  boolean existsByAvatarAndItem(Avatar avatar, Item item);
}
