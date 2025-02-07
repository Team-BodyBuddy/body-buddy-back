package BodyBuddy.demo.domain.item.DTO;

import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PurchaseDTO {
  // ✅ 구매 요청 DTO
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RequestDTO {
    private Long memberId;
    private Long itemId;

  }

  // 구매 응답 DTO
  @Getter
  @AllArgsConstructor
  public static class ResponseDTO {
    private Long itemId;
    private String name;
    private String imagePath;
    private Long price;
    private ItemStatus status;
    private LocalDateTime purchasedAt;
    private Long remainingPoints;

  }
}
