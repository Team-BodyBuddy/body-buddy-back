package BodyBuddy.demo.domain.avatar.service;

import BodyBuddy.demo.domain.avatar.dto.AvatarDecoDTO;
import BodyBuddy.demo.domain.avatar.dto.AvatarDecoDTO.EquippedItem;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.memberItem.repository.MemberItemRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarDecoService {

  private final AvatarRepository avatarRepository;
  private final MemberItemRepository memberItemRepository;

  @Transactional
  public AvatarDecoDTO.AvatarInfo getLatestAvatarInfo(Long memberId) {
    Avatar avatar = avatarRepository.findByMemberId(memberId)
        .orElseThrow(() -> new IllegalArgumentException("memberId: " + memberId + " 의 아바타 정보를 찾을 수 없습니다."));

    // 최신 아바타 스킨 가져오기
    String avatarSkinImage = avatar.getAvatarSkin().getImagePath();

    // 현재 착용 중인 아이템 가져오기
    List<EquippedItem> equippedItems = memberItemRepository.findWearingItemsByAvatar(avatar.getId()).stream()
        .flatMap(memberItem -> memberItem.getItems().stream()
            .map(item -> new AvatarDecoDTO.EquippedItem(
                item.getName(),
                item.getImagePath(),
                item.getType()
            )))
        .collect(Collectors.toList());

    return AvatarDecoDTO.AvatarInfo.builder()
        .imagePath(avatarSkinImage)
        .equippedSkin(avatarSkinImage)
        .equippedItems(equippedItems)
        .build();
  }
}
