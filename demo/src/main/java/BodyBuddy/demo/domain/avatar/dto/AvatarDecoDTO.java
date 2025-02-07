package BodyBuddy.demo.domain.avatar.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record AvatarDecoDTO(
    Long totalPoints,
    String nickname,
    AvatarInfo avatarInfo
)
{
  @Builder
  public record AvatarInfo(
      String imagePath,
      String equippedSkin,
      List<EquippedItem> equippedItems
  ) {}

  @Builder
  public record EquippedItem(
      String name,
      String imagePath,
      String type
  ) {}
}