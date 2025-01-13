package BodyBuddy.demo.domain.web.controller;

import BodyBuddy.demo.domain.avatar.DTO.AvatarDTO;
import BodyBuddy.demo.domain.inBody.DTO.InBodyDTO;
import BodyBuddy.demo.domain.web.Service.MainPageService;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import BodyBuddy.demo.global.common.point.DTO.PointDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
@Tag(name="MainPage", description="메인페이지 API")
public class MainPageController {

  private final MainPageService mainPageService;

  public MainPageController(MainPageService mainPageService) {
    this.mainPageService = mainPageService;
  }

  /**
   * 포인트 총합 조회 API
   */
  @Operation(summary = "포인트 총량 조회", description = "회원의 총 포인트를 반환.")
  @GetMapping("/{memberId}/points/total")
  public ResponseEntity<PointDTO.Response> getTotalPoints(@PathVariable Long memberId) {
    PointDTO.Response totalPoints = mainPageService.getTotalPoints(memberId);
    return ResponseEntity.ok(totalPoints);
  }

  /**
   * 멤버 정보 조회 API
   */
  @Operation(summary = "멤버 정보 조회", description = "회원의 닉네임, 레벨, 경험치 정보 반환.")
  @GetMapping("/{memberId}/info")
  public ResponseEntity<MemberDTO> getMemberInfo(@PathVariable Long memberId) {
    MemberDTO memberInfo = mainPageService.getMemberInfo(memberId);
    return ResponseEntity.ok(memberInfo);
  }

  /**
   * 아바타 정보 조회 API
   */
  @Operation(summary = "아바타 정보 조회", description = "회원 아바타 이미지와 최신 스킨 정보를 반환.")
  @GetMapping("/{memberId}/avatar")
  public ResponseEntity<AvatarDTO> getAvatar(@PathVariable Long memberId) {
    AvatarDTO avatarInfo = mainPageService.getLatestAvatarInfo(memberId);
    return ResponseEntity.ok(avatarInfo);
  }

  /**
   * 인바디 정보 비교 API
   */
  @Operation(summary = "인바디 정보 조회", description = "최근 2개의 인바디 데이터를 비교하여 체중, 근육량, 체지방률의 변화를 반환합니다.")
  @GetMapping("/{memberId}/inbody")
  public ResponseEntity<InBodyDTO.RecentComparison> getRecentInBody(@PathVariable Long memberId) {
    InBodyDTO.RecentComparison comparison = mainPageService.getRecentComparison(memberId);
    return ResponseEntity.ok(comparison);
  }

  /**
   * 체중 정보 조회 API
   */
  @Operation(summary = "체중 변화 조회", description = "회원의 최신 5개의 체중 기록을 반환합니다.")
  @GetMapping("/{memberId}/inbody/weight-history")
  public ResponseEntity<List<InBodyDTO.WeightHistory>> getWeightHistory(@PathVariable Long memberId) {
    List<InBodyDTO.WeightHistory> weightHistory = mainPageService.getWeightHistory(memberId);
    return ResponseEntity.ok(weightHistory);
  }

}
