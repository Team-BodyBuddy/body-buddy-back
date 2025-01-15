package BodyBuddy.demo.domain.item.DTO;

import BodyBuddy.demo.global.common.Category;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemResponse {

  private Category category;
  private List<ItemResponse> items;

}
