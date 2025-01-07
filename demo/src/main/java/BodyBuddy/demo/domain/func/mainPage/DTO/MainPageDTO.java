package BodyBuddy.demo.domain.func.mainPage.DTO;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainPageDTO {
    private String nickName;
    private String imagePath;
    private Long level;
    private Long points;
    private InBodyDTO recentInBody;
    private String weightChange;
    private AvatarDTO avatar;
    private List<WeightChangeDTO> weightHistory;
}

