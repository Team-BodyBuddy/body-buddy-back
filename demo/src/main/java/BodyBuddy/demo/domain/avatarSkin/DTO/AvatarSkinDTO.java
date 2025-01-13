package BodyBuddy.demo.domain.avatarSkin.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarSkinDTO {
  private Long id;         // 스킨 ID
  private String name;     // 스킨 이름
  private String imagePath; // 이미지 경로
}