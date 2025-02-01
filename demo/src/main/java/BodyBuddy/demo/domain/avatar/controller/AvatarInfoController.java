package BodyBuddy.demo.domain.avatar.controller;

import BodyBuddy.demo.domain.avatar.dto.AvatarInfoRequestDTO;
import BodyBuddy.demo.domain.avatar.service.AvatarService;
import BodyBuddy.demo.domain.inbody.dto.AvatarInBodyDTO;
import BodyBuddy.demo.domain.inbody.dto.WeightHistoryListDTO;
import BodyBuddy.demo.domain.inbody.service.InBodyService;
import BodyBuddy.demo.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mainPage")
@Tag(name = "Avatar", description = "아바타 관련 API")
@RequiredArgsConstructor
public class AvatarInfoController {

  private final AvatarService avatarService;
  private final MemberService memberService;
  private final InBodyService inBodyService;

  // 포인트 총 수량 조회
  @Operation(summary = "포인트 총량 조회", description = "회원의 총 포인트를 반환합니다.")
  @GetMapping("/{memberId}/points/total")
  public ResponseEntity<Long> getTotalPoints(@Parameter(description = "회원 ID", required = true) @PathVariable Long memberId) {
    Long totalPoints = avatarService.getTotalPoints(memberId);
    return ResponseEntity.ok(totalPoints);
  }

  // 닉네임 조회

  @Operation(summary = "닉네임 조회", description = "회원의 닉네임을 반환합니다.")
  @GetMapping("/{memberId}/nickname")
  public ResponseEntity<String> getNickname(@PathVariable Long memberId) {
    String nickname = memberService.getNicknameByMemberId(memberId);
    return ResponseEntity.ok(nickname);
  }

  // 아바타 스킨 , 레벨+ 경험치 조회

  @Operation(summary = "아바타 기본 정보 조회", description = "아바타 스킨,레벨,경험치를 반환합니다.")
  @GetMapping("/{memberId}/avatarInfo")
  public ResponseEntity<AvatarInfoRequestDTO> getAvatarInfo(@PathVariable Long memberId) {
    AvatarInfoRequestDTO avatarInfo = avatarService.getAvatarInfoRequestDTO(memberId);
    return ResponseEntity.ok(avatarInfo);
  }

  // 인바디 정보 조회

  @Operation(summary = "인바디 정보 조회", description = "체중,골격근량,체지방률을 반환 후 비교합니다.")
  @GetMapping("/{memberId}/inBody")
  public ResponseEntity<AvatarInBodyDTO> getAvatarInBody(@PathVariable Long memberId) {
    return ResponseEntity.ok(inBodyService.getAvatarInBody(memberId));
  }

  // 최근 5개 체중 조회
  @Operation(summary = "인바디 체중 기록 조회", description = "최근 5개의 체중을 반환합니다.")
  @GetMapping("/{memberId}/inBody/weightHistory")
  public ResponseEntity<WeightHistoryListDTO> getWeightHistory(@PathVariable Long memberId) {
    WeightHistoryListDTO weightHistory = inBodyService.getRecentWeightHistory(memberId);
    return ResponseEntity.ok(weightHistory);
  }

}
