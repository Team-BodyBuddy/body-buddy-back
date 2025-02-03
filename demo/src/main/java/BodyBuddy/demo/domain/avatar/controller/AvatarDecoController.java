package BodyBuddy.demo.domain.avatar.controller;

import BodyBuddy.demo.domain.avatar.dto.AvatarDecoDTO;
import BodyBuddy.demo.domain.avatar.service.AvatarDecoService;
import BodyBuddy.demo.domain.avatar.service.AvatarService;
import BodyBuddy.demo.domain.item.DTO.CategoryItemDTO;
import BodyBuddy.demo.domain.item.DTO.PurchaseDTO;
import BodyBuddy.demo.domain.item.service.ItemService;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deco")
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
   * DECO 페이지 통합 정보 조회
   * 웹이라면 프론트 측에서 조회를 할 때 최종적으로 전체 페이지에 대한 API도 의미가 있을지는 모르겠습니다.
   * 모바일에서는 통합 API 사용도 유용할 수 있다고 하는데 상의 후 작성하도록 하겠습니다.
   */

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