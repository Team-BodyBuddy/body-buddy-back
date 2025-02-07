package BodyBuddy.demo.domain.avatar.controller;

import BodyBuddy.demo.domain.avatar.dto.AvatarDecoDTO;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.service.AvatarDecoService;
import BodyBuddy.demo.domain.avatar.service.AvatarService;
import BodyBuddy.demo.domain.item.DTO.CategoryItemDTO;
import BodyBuddy.demo.domain.item.DTO.PurchaseDTO;
import BodyBuddy.demo.domain.item.service.ItemService;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deco")
@Tag(name = "Deco", description = "DECO 관련 API")
@RequiredArgsConstructor
public class AvatarDecoController {

  private final AvatarService avatarService;
  private final AvatarDecoService avatarDecoService;
  private final MemberService memberService;
  private final ItemService itemService;

  /**
   * 포인트 총합 조회 API
   */

  @Operation(summary = "포인트 총량 조회", description = "회원의 총 포인트를 반환.")
  @GetMapping("/{memberId}/points")
  public ApiResponse<Long> getTotalPoints(@PathVariable Long memberId) {
    Long totalPoints = avatarService.getTotalPoints(memberId);
    return ApiResponse.of(SuccessStatus.POINT_SUCCESS, totalPoints);
  }

  /**
   * 멤버 닉네임 조회 API
   */

  @Operation(summary = "닉네임 조회", description = "회원의 닉네임을 반환.")
  @GetMapping("/{memberId}/nickname")
  public ApiResponse<String> getMemberNickname(@PathVariable Long memberId) {
    String nickname = memberService.getNicknameByMemberId(memberId);
    return ApiResponse.of(SuccessStatus.USERINFO_SUCCESS, nickname);
  }

  /**
   * 아바타 정보 조회 API
   */

  @Operation(summary = "아바타 정보 조회", description = "회원 아바타 이미지와 최신 스킨 정보를 반환.")
  @GetMapping("/{memberId}/avatar")
  public ApiResponse<AvatarDecoDTO.AvatarInfo> getAvatar(@PathVariable Long memberId) {
    AvatarDecoDTO.AvatarInfo avatarInfo = avatarDecoService.getLatestAvatarInfo(memberId);
    return ApiResponse.of(SuccessStatus.USERINFO_SUCCESS, avatarInfo);
  }

  /**
   * 카테고리 별 아이템 조회 API
   */

  @Operation(summary = "카테고리별 아이템 조회", description = "모든 아이템을 카테고리별로 조회하며, 구매한 아이템은 ACTIVE, 구매하지 않은 아이템은 INACTIVE로 표시")
  @GetMapping("/{memberId}/itemCategory")
  public ApiResponse<List<CategoryItemDTO>> getAllItemsByCategory(@PathVariable Long memberId) {
    List<CategoryItemDTO> categorizedItems = itemService.getAllItemsByCategory(memberId);
    return ApiResponse.of(SuccessStatus.USERINFO_SUCCESS, categorizedItems);
  }

  /**
   * 아이템 구매 시 상태 변경 API
   * 구매시 포인트 차감 필요 + 에러 핸들링
   */

  @Operation(summary = "아이템 구매", description = "유저가 특정 아이템을 구매하면 해당 아이템을 ACTIVE 상태로 변경")
  @PostMapping("/{memberId}/item/{itemId}")
  public ApiResponse<PurchaseDTO.ResponseDTO> purchaseItem(@RequestBody PurchaseDTO.RequestDTO requestDTO) {
    PurchaseDTO.ResponseDTO responseDTO = itemService.purchaseItem(requestDTO);
    return ApiResponse.of(SuccessStatus.PURCHASE_SUCCESS, responseDTO);
  }

  /**
   * 아바타 스킨 변경 API
   */

  @Operation(summary = "아바타 스킨 변경", description = "유저가 특정 레벨 달성시에 아바타 스킨 변경")
  @PatchMapping("/{memberId}/level/{newLevel}")
  public ApiResponse<Avatar> updateAvatarLevel(
      @PathVariable Long memberId,
      @PathVariable Long newLevel
  ) {
    Avatar updatedAvatar = avatarService.updateAvatarLevel(memberId, newLevel);
    return ApiResponse.of(SuccessStatus.USERINFO_SUCCESS, updatedAvatar);
  }




  /**
  public DecoPageDTO getDecoPageInfo(Long memberId) {
    Long points = getTotalPoints(memberId);
    String nickname = getMemberNickname(memberId);
    DecoPageDTO.AvatarInfo avatarInfo = getLatestAvatarInfo(memberId);

    return DecoPageDTO.builder()
        .totalPoints(points)
        .nickname(nickname)
        .avatarInfo(avatarInfo)
        .build();
  }
  */

}