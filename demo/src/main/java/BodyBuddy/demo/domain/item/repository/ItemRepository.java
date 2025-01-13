package BodyBuddy.demo.domain.item.repository;

import BodyBuddy.demo.domain.item.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
  // 특정 카테고리의 모든 아이템 조회 (활성화 여부와 무관)
  //List<Item> findByCategoryId(Long categoryId);
}
