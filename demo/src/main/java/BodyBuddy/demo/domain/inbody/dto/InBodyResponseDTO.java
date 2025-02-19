package BodyBuddy.demo.domain.inbody.dto;

import BodyBuddy.demo.global.common.commonEnum.InBodyStatus;
import lombok.Getter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InBodyResponseDTO {
  private final LatestData latestData;
  private final List<HistoryData> historyData;

  @Getter
  @RequiredArgsConstructor
  public static class LatestData {
    private final Float weight;
    private final Float muscle;
    private final Float bodyFat;
    private final InBodyStatus weightStatus;
    private final InBodyStatus muscleStatus;
    private final InBodyStatus bodyFatStatus;
  }

  @Getter
  @RequiredArgsConstructor
  public static class HistoryData {
    private final Float weight;
    private final Float muscle;
    private final Float bodyFat;
  }
}
