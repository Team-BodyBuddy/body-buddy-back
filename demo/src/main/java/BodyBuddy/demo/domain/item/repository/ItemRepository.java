package BodyBuddy.demo.domain.item.repository;

import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.global.common.commonEnum.ItemCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
  // 특정 카테고리에 속한 모든 아이템 조회
  List<Item> findByCategory(ItemCategory category);
  // 아이템 구매위한 특정 아이템 조회
  Optional<Item> findById(Long itemId);
}
