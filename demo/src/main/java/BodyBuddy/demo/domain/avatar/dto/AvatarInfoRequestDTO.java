package BodyBuddy.demo.domain.avatar.dto;

import BodyBuddy.demo.domain.avatar.entity.Avatar;

public record AvatarInfoRequestDTO (
    String imagePath,
    Long level,
    Long exp
){
    public static AvatarInfoRequestDTO from(Avatar avatar) {

      // 가장 최신 스킨의 imagePath 가져오기 (없으면 null)
      String imagePath = avatar.getAvatarSkins().stream()
          .findFirst()
          .map(skin -> skin.getImagePath())
          .orElse(null);

      return new AvatarInfoRequestDTO(
          imagePath,
          avatar.getLevel(),
          avatar.getExp());
  }
}
