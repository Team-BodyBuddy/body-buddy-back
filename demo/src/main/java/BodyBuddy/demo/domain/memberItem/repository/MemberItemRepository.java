package BodyBuddy.demo.domain.memberItem.repository;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
  // 특정 회원이 소유한 아이템 ID 리스트 조회
  @Query("SELECT mi.item.id FROM MemberItem mi WHERE mi.member.id = :memberId")
  List<Long> findItemIdsByMemberId(@Param("memberId") Long memberId);
}
