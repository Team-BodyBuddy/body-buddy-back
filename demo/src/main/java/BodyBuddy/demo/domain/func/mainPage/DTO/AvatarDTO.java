package BodyBuddy.demo.domain.func.mainPage.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDTO {
    private String imagePath;
    private Long level;
    private long exp;
    private List<String> skins;
}
