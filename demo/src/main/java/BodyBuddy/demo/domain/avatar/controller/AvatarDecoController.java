package BodyBuddy.demo.domain.avatar.controller;

import BodyBuddy.demo.domain.avatar.dto.AvatarDecoDTO;
import BodyBuddy.demo.domain.avatar.service.AvatarDecoService;
import BodyBuddy.demo.domain.avatar.service.AvatarService;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

//  @Operation(summary = "카테고리별 아이템 조회", description = "특정 카테고리에 속한 아이템 목록을 반환")
//  @GetMapping("/{memberId}/items")
//  public ApiResponse<CategoryItemResponse> getItemsByCategory(
//      @PathVariable Long memberId,
//      @RequestParam(name = "category", defaultValue = "DEFAULT") String category
//  ) {
//    CategoryItemResponse response = decoPageService.getItemsByCategory(memberId, category);
//    return ApiResponse.of(SuccessStatus.USERINFO_SUCCESS, response);
//  }

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