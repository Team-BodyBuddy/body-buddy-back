package BodyBuddy.demo.domain.avatar.controller;

import BodyBuddy.demo.domain.avatar.dto.AvatarInfoResponseDTO;
import BodyBuddy.demo.domain.avatar.service.AvatarService;
import BodyBuddy.demo.domain.inbody.dto.InBodyResponseDTO;
import BodyBuddy.demo.domain.inbody.dto.InBodyResponseDTO.HistoryData;
import BodyBuddy.demo.domain.inbody.dto.WeightHistoryListDTO;
import BodyBuddy.demo.domain.inbody.service.InBodyService;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mainPage")
@Tag(name = "Avatar", description = "아바타 관련 API")
@RequiredArgsConstructor
public class AvatarInfoController {

  private final AvatarService avatarService;
  private final MemberService memberService;
  private final InBodyService inBodyService;

  // 포인트 총 수량 조회
  @Operation(summary = "포인트 총량 조회", description = "회원의 총 포인트를 반환합니다.")
  @GetMapping("/{memberId}/points/total")
  public ApiResponse<Long> getTotalPoints(
      @Parameter(description = "회원 ID", required = true) @PathVariable("memberId") Long memberId){
    Long totalPoints = avatarService.getTotalPoints(memberId);
    return ApiResponse.of(SuccessStatus.POINT_SUCCESS, totalPoints);
  }

  // 아바타 스킨 , 레벨+ 경험치, 닉네임 조회
  @Operation(summary = "아바타 기본 정보 조회", description = "아바타 스킨,레벨,경험치를 반환합니다.")
  @GetMapping("/{memberId}/avatarInfo")
  public ApiResponse<AvatarInfoResponseDTO> getAvatarInfo(@PathVariable Long memberId) {
    AvatarInfoResponseDTO avatarInfo = avatarService.getAvatarInfoRequestDTO(memberId);
    return ApiResponse.of(SuccessStatus.USERINFO_SUCCESS, avatarInfo);
  }

  // 인바디 정보 조회
  @Operation(summary = "인바디 정보 조회", description = "체중,골격근량,체지방률을 반환 후 비교합니다.")
  @GetMapping("/{memberId}/inBody")
  public ApiResponse<InBodyResponseDTO.LatestData> getLatestInBody(@PathVariable Long memberId) {
    InBodyResponseDTO.LatestData latestData = inBodyService.getLatestInBody(memberId);
    return ApiResponse.of(SuccessStatus.INBODY_SUCCESS, latestData);
  }

  // 최근 5개 체중 조회
  @Operation(summary = "인바디 체중 기록 조회", description = "최근 5개의 체중을 반환합니다.")
  @GetMapping("/{memberId}/inBody/weightHistory")
  public ApiResponse<List<HistoryData>> getInBodyHistory(@PathVariable Long memberId) {
    List<InBodyResponseDTO.HistoryData> historyData = inBodyService.getRecentInBodyHistory(memberId);
    return ApiResponse.of(SuccessStatus.INBODY_SUCCESS, historyData);
  }

}
