package BodyBuddy.demo.domain.web.controller;

import BodyBuddy.demo.domain.avatar.DTO.AvatarDTO;
import BodyBuddy.demo.domain.web.Service.DecoPageService;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import BodyBuddy.demo.global.common.point.DTO.PointDTO;
import BodyBuddy.demo.global.common.point.DTO.PointDTO.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deco")
@Tag(name="Deco Page",description = "꾸미기 페이지 API")
public class DecoPageController {
  private final DecoPageService decoPageService;

  public DecoPageController(DecoPageService decoPageService) {
    this.decoPageService = decoPageService;
  }

  /**
   * 1. 포인트 총합 조회
   * 2. 멤버 정보 조회 (닉네임)
   * 3. 아바타 스킨 조회
   * 4. 카테고리 별 아이템 조회
   *    : 등록되어 있는 모든 아이템 카테고리 별 노출
   *    : 보유하고 있는 아이템은 진하게 표현
   *    : 아직 보유하지 않은 아이템은 연하게 표현
   * 5. 예외 처리
   *    : 포인트 부족시 -> 포인트가 부족합니다
   *    : 포인트 충분하면 -> 구매 가능
   */

  /**
   * 포인트 총합 조회 API
   */

  @Operation(summary = "포인트 총량 조회", description = "회원의 총 포인트를 반환합니다.")
  @GetMapping("/{memberId}/points/total")
  public ResponseEntity<PointDTO.Response> getTotalPoints(@PathVariable Long memberId) {
    PointDTO.Response response = decoPageService.getTotalPoints(memberId);
    return ResponseEntity.ok(response);
  }

  /**
   * 멤버 정보 조회 API
   */

  @GetMapping("/api/members/{memberId}/nickname")
  public ResponseEntity<MemberDTO.Response> getMemberNickname(@PathVariable Long memberId) {
    MemberDTO.Response response = decoPageService.getMemberInfo(memberId);
    return ResponseEntity.ok(response);
  }

  /**
   * 아바타 정보 조회 API
   */

  @Operation(summary = "아바타 정보 조회", description = "회원 아바타 이미지와 최신 스킨 정보를 반환.")
  @GetMapping("/{memberId}/avatar")
  public ResponseEntity<AvatarDTO> getAvatar(@PathVariable Long memberId) {
    AvatarDTO avatarInfo = decoPageService.getLatestAvatarInfo(memberId);
    return ResponseEntity.ok(avatarInfo);
  }

  /**
   * 카테고리 별 아이템 정보 조회 API
   */




}
