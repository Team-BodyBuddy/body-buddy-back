package BodyBuddy.demo.domain.mainPage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class MainPageDTO {
    private String nickName;
    private String imagePath;
    private Long level;
    private Long points;
    private InBodyDTO recentInBody;
}

