package BodyBuddy.demo.domain.item.repository;

import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.global.common.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
  // 카테고리에 속한 모든 아이템 조회
  List<Item> findAllByCategory(Category category);
}
