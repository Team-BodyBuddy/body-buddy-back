package BodyBuddy.demo.domain.avatar.dto;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;

public record AvatarInfoResponseDTO (
    String imagePath,
    Long level,
    Long exp,
    //닉네임
    String nickname
){
  public static AvatarInfoResponseDTO from(Avatar avatar) {

    //현재 아바타의 스킨 가져오기
    AvatarSkin avatarSkin = avatar.getAvatarSkin();

    return new AvatarInfoResponseDTO(
        avatarSkin != null ? avatarSkin.getImagePath() : null, // 스킨이 없을 경우 null 처리
        avatar.getLevel(),
        avatar.getExp(),
        avatar.getMember().getNickname()
    );
  }
}
