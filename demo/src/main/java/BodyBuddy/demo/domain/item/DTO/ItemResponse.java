package BodyBuddy.demo.domain.item.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

  private Long id;
  private String name;
  private String imagePath;
  private boolean isOwned;

}