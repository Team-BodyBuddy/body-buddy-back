package BodyBuddy.demo.domain.item.service;

import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.domain.item.DTO.CategoryItemDTO;
import BodyBuddy.demo.domain.item.repository.ItemRepository;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.domain.memberItem.repository.MemberItemRepository;
import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

  private ItemRepository itemRepository;
  private MemberItemRepository memberItemRepository;

  public List<CategoryItemDTO> getAllItemsByCategory(Long memberId) {
    // 멤버가 구매한 아이템 조회
    List<MemberItem> purchasedItems = memberItemRepository.findByAvatarMemberId(memberId);
    Set<Long> purchasedItemIds = purchasedItems.stream()
        .flatMap(memberItem -> memberItem.getItems().stream().map(Item::getId))
        .collect(Collectors.toSet());

    return itemRepository.findAll().stream()
        .map(item -> new CategoryItemDTO(
            item.getId(),
            item.getName(),
            item.getImagePath(),
            item.getPrice(),
            purchasedItemIds.contains(item.getId()) ? ItemStatus.ACTIVE : ItemStatus.INACTIVE
        ))
        .collect(Collectors.toList());
  }
}
