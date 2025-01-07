package BodyBuddy.demo.domain.func.mainPage.controller;


import BodyBuddy.demo.domain.func.mainPage.DTO.AvatarDTO;
import BodyBuddy.demo.domain.func.mainPage.DTO.MainPageDTO;
import BodyBuddy.demo.domain.func.mainPage.DTO.WeightChangeDTO;
import BodyBuddy.demo.domain.func.mainPage.service.MainPageService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/mainpages")
@Tag(name="MainPage",description="메인페이지 API")
public class MainPageController {

  private final MainPageService mainPageService;

  public MainPageController(MainPageService mainPageService) {
    this.mainPageService = mainPageService;
  }

  /**
   * 메인 페이지 전체 API
   */

  @Operation(summary = "메인 페이지 데이터 조회", description = "포인트,아바타,인바디,체중 데이터 반환")
  @GetMapping("/{memberId}")
  public ResponseEntity<MainPageDTO> getMainPage(@PathVariable Long memberId) {
    MainPageDTO mainPageData = mainPageService.getMainPage(memberId);
    return ResponseEntity.ok(mainPageData);
  }

  /**
   * 포인트 조회 API
   */
  @Operation(summary = "포인트 총량 조회", description = "회원의 총 포인트를 반환.")
  @GetMapping("/{memberId}/points/total")
  public ResponseEntity<Long> getTotalPoints(@PathVariable Long memberId) {
    Long totalPoints = mainPageService.getTotalPoints(memberId);
    return ResponseEntity.ok(totalPoints);
  }
  /**
   * 아바타 정보 조회 API
   */
  @Operation(summary = "아바타 정보 조회", description = "회원 닉네임, 레벨, 경험치, 아바타 정보 반환.")
  @GetMapping("/{memberId}/avatar")
  public ResponseEntity<AvatarDTO> getAvatarInfo(@PathVariable Long memberId) {
    AvatarDTO avatarInfo = mainPageService.getAvatarInfo(memberId);
    return ResponseEntity.ok(avatarInfo);
  }

  /**
   * 인바디 정보 조회 API
   */
  @Operation(summary = "인바디 정보 조회", description = "회원의 체중, 골격근량, 체지방률 정보 + 비교 데이터 반환.")
  @GetMapping("/{memberId}/inbody")
  public ResponseEntity<Map<String, Object>> getRecentAndPreviousInBody(@PathVariable Long memberId) {
    Map<String, Object> inBodyData = mainPageService.getRecentAndPreviousInBody(memberId);
    return ResponseEntity.ok(inBodyData);
  }

  /**
   * 최근 5개의 체중 정보 반환
   */
  @Operation(summary = "체중 변화 조회", description = "회원의 최신 5개의 체중 기록을 반환합니다.")
  @GetMapping("/{memberId}/inbody/weight-history")
  public ResponseEntity<List<WeightChangeDTO>> getWeightHistory(@PathVariable Long memberId) {
    List<WeightChangeDTO> weightHistory = mainPageService.getWeightHistory(memberId);
    return ResponseEntity.ok(weightHistory);
  }


}
