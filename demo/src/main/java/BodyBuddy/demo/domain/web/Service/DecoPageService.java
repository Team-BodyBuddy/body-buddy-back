package BodyBuddy.demo.domain.web.Service;


import BodyBuddy.demo.domain.avatar.DTO.AvatarDTO;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.domain.avatarSkin.repository.AvatarSkinRepository;
import BodyBuddy.demo.domain.inBody.repository.InBodyRepository;
import BodyBuddy.demo.domain.item.repository.ItemRepository;
import BodyBuddy.demo.domain.item.DTO.CategoryItemResponse;
import BodyBuddy.demo.domain.item.DTO.ItemResponse;
import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.domain.memberItem.repository.MemberItemRepository;
import BodyBuddy.demo.global.common.Category;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import BodyBuddy.demo.global.common.member.entity.Member;
import BodyBuddy.demo.global.common.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.point.DTO.PointDTO;
import BodyBuddy.demo.global.common.point.repository.PointRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecoPageService {

  private final MemberRepository memberRepository;
  private final AvatarRepository avatarRepository;
  private final AvatarSkinRepository avatarSkinRepository;
  private final InBodyRepository inBodyRepository;
  private final ItemRepository itemRepository;
  private final MemberItemRepository memberItemRepository;

  /**
   포인트 총합 조회 로직
   */

  public PointDTO.Response getTotalPoints(Long memberId) {
    // 멤버 조회 (존재하지 않으면 예외 발생)
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

    return PointDTO.Response.builder()
        .amount(member.getTotalPoints())
        .build();
  }

  /**
   * 멤버 정보 조회 로직
   */

  public MemberDTO.Response getMemberInfo(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

    return MemberDTO.Response.builder()
        .id(member.getId())
        .nickName(member.getNickName())
        .build();
  }

  /**
   * 아바타 스킨 조회 로직
   */
  public AvatarDTO getLatestAvatarInfo(Long memberId) {
    // 1. 멤버 조회
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다."));

    // 2. 멤버와 연결된 아바타 조회
    Avatar avatar = member.getAvatars().stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("멤버에게 아바타가 없습니다."));

    // 3. 아바타와 연결된 최신 스킨 조회
    AvatarSkin latestSkin = avatarSkinRepository.findTopByAvatarIdOrderByIdDesc(avatar.getId())
        .orElseThrow(() -> new IllegalArgumentException("아바타 스킨이 없습니다"));

    // 4. DTO 생성 및 반환
    return new AvatarDTO(
        avatar.getId(),
        latestSkin.getImagePath(),
        new AvatarDTO.Response(latestSkin.getId(), latestSkin.getName(), latestSkin.getImagePath())
    );
  }

  /**
   * 카테고리별 아이템 조회 로직
   */
  public CategoryItemResponse getItemsByCategory(Long memberId, Category category) {
    // 1. 카테고리에 속한 모든 아이템 조회
    List<Item> items = itemRepository.findAllByCategory(category);

    // 2. 회원이 소유한 아이템 ID 리스트 조회
    List<Long> ownedItemIds = memberItemRepository.findItemIdsByMemberId(memberId);

    // 3. 응답 데이터 구성
    List<ItemResponse> itemResponses = items.stream()
        .map(item -> ItemResponse.builder()
            .id(item.getId())
            .name(item.getName())
            .imagePath(item.getImagePath())
            .isOwned(ownedItemIds.contains(item.getId()))
            .build())
        .collect(Collectors.toList());

    return CategoryItemResponse.builder()
        .category(category)
        .items(itemResponses)
        .build();
  }


}
