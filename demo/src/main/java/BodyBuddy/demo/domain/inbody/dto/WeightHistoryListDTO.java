package BodyBuddy.demo.domain.inbody.dto;

import BodyBuddy.demo.domain.inbody.entity.InBody;
import java.time.LocalDateTime;
import java.util.List;

public record WeightHistoryListDTO(List<WeightHistory> weightHistory) {

  public WeightHistoryListDTO(List<WeightHistory> weightHistory) {
    this.weightHistory = weightHistory;
  }

  // 체중 여러건 저장
  public static WeightHistoryListDTO from(List<InBody> inBodies) {
    List<WeightHistory> weightHistoryList = inBodies.stream()
        .map(WeightHistory::from)
        .toList();

    return new WeightHistoryListDTO(weightHistoryList);
  }

  // 체중 단건 저장
  public record WeightHistory(Float weight, LocalDateTime createdAt) {
    public static WeightHistory from(InBody inBody) {
      return new WeightHistory(inBody.getWeight(), inBody.getCreatedAt());
    }
  }
}