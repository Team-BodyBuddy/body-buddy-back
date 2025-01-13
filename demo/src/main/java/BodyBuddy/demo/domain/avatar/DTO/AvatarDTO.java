package BodyBuddy.demo.domain.avatar.DTO;

import BodyBuddy.demo.domain.avatarSkin.DTO.AvatarSkinDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDTO {

    private Long avatarId;              // 아바타 ID
    private String imagePath;           // 아바타 기본 이미지 경로
    private Response latestSkin;        // 최신 스킨 정보

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;                // 스킨 ID
        private String name;            // 스킨 이름
        private String imagePath;       // 스킨 이미지 경로
    }

}
