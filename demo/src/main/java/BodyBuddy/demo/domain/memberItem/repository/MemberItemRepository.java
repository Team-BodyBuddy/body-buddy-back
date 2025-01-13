package BodyBuddy.demo.domain.memberItem.repository;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
  // 특정 회원이 특정 아이템을 소유하고 있는지 확인
  boolean existsByMemberIdAndItemId(Long memberId, Long itemId);
}
