package BodyBuddy.demo.domain.func.mainPage.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeightChangeDTO {
  private Float weight;
  private LocalDateTime date;
}
