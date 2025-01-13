package BodyBuddy.demo.domain.web.controller;

import BodyBuddy.demo.domain.web.Service.DecoPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
   * 1. 포인트 총합 조회
   */



}
