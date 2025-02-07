package BodyBuddy.demo.domain.avatar.dto;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;

public record AvatarInfoRequestDTO (
    String imagePath,
    Long level,
    Long exp
){
  public static AvatarInfoRequestDTO from(Avatar avatar) {

    //현재 아바타의 스킨 가져오기
    AvatarSkin avatarSkin = avatar.getAvatarSkin();

    return new AvatarInfoRequestDTO(
        avatarSkin != null ? avatarSkin.getImagePath() : null, // 스킨이 없을 경우 null 처리
        avatar.getLevel(),
        avatar.getExp()
    );
  }
}
