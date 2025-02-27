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
import BodyBuddy.demo.global.apiPayload.code.error.AvatarErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.ItemErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.common.commonEnum.ItemCategory;
import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

  private final AvatarRepository avatarRepository;
  private final ItemRepository itemRepository;
  private final MemberItemRepository memberItemRepository;

  /**
   * 멤버가 구매한 아이템 조회
   */



  /**
   * 아이템 구매 서비스
   */
  @Transactional
  public PurchaseDTO.ResponseDTO purchaseItem(PurchaseDTO.RequestDTO requestDTO) {
    // 1️⃣ 아이템 조회
    Item item = itemRepository.findById(requestDTO.getItemId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

    // 2️⃣ 사용자 조회
    Avatar avatar = avatarRepository.findByMemberId(requestDTO.getMemberId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

    // 3️⃣ 이미 구매한 아이템인지 확인
    boolean alreadyPurchased = memberItemRepository.existsByAvatarAndItem(avatar, item);
    if (alreadyPurchased) {
      throw new IllegalStateException("이미 구매한 아이템입니다.");
    }

    // 4️⃣ 포인트 부족한 경우 예외 발생
    if (avatar.getPoint() < item.getPrice()) {
      throw new IllegalStateException("포인트가 부족합니다.");
    }

    // 5️⃣ Avatar의 포인트 차감
    avatar.usePoints(item.getPrice());
    avatarRepository.save(avatar);

    // 6️⃣ `MemberItem`을 생성하여 아이템 구매 내역 추가
    MemberItem memberItem = memberItemRepository.save(
        MemberItem.builder()
            .avatar(avatar)
            .item(item) // ✅ 변경: MemberItem → Item 직접 참조
            .usedPoints(item.getPrice())
            .isEquipped(false)
            .purchasedAt(LocalDateTime.now())
            .build()
    );

    // 7️⃣ 응답 객체 반환
    return new PurchaseDTO.ResponseDTO(
        item.getId(),
        item.getName(),
        item.getImagePath(),
        item.getPrice(),
        ItemStatus.ACTIVE, // ✅ 아이템을 구매했으므로 상태를 ACTIVE로 설정
        LocalDateTime.now(),
        avatar.getPoint()
    );
  }
}
