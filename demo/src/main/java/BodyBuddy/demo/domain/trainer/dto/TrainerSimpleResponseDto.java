package BodyBuddy.demo.domain.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerSimpleResponseDto {
    private Long id;
    private String name;
    private String gender;
    private int age;
}
