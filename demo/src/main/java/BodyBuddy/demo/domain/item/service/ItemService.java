package BodyBuddy.demo.domain.item.service;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.item.DTO.PurchaseDTO;
import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.domain.item.DTO.CategoryItemDTO;
import BodyBuddy.demo.domain.item.repository.ItemRepository;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.domain.memberItem.repository.MemberItemRepository;
import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

  private AvatarRepository avatarRepository;
  private ItemRepository itemRepository;
  private MemberItemRepository memberItemRepository;

  /**
   * 멤버가 구매한 아이템 조회
   */
  public List<CategoryItemDTO> getAllItemsByCategory(Long memberId) {
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

  /**
   * 아이템 구매 서비스
   */

  @Transactional
  public PurchaseDTO.ResponseDTO purchaseItem(PurchaseDTO.RequestDTO requestDTO) {
    Item item = itemRepository.findById(requestDTO.getItemId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

    if (item.getStatus() == ItemStatus.ACTIVE) {
      throw new IllegalStateException("이미 구매한 아이템입니다.");
    }

    Avatar avatar = avatarRepository.findByMemberId(requestDTO.getMemberId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

    MemberItem memberItem = MemberItem.builder()
        .avatar(avatar)
        .usedPoints(item.getPrice())
        .isEquipped(false)
        .build();

    memberItemRepository.save(memberItem);
    item.activate(memberItem);
    itemRepository.save(item);

    return new PurchaseDTO.ResponseDTO(
        item.getId(),
        item.getName(),
        item.getImagePath(),
        item.getPrice(),
        item.getStatus(),
        LocalDateTime.now()
    );
  }

}
