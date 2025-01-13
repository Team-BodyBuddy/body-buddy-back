package BodyBuddy.demo.global.common.point.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class PointDTO {

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Response{
    private Long amount;
  }

}
