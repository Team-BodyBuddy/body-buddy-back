package BodyBuddy.demo.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MemberUpdateRequestDto {
    private final String region;
    private final String gym;
    private final int height;
    private final int weight;
}