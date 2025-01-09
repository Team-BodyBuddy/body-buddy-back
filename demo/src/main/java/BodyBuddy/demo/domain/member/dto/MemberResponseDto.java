package BodyBuddy.demo.domain.member.dto;

import BodyBuddy.demo.domain.member.memberEnum.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private final String nickname;
    private final int level;
    private final String name;
    private final Gender gender;
    private final String birthDate;
    private final String region;
    private final String gym;
    private final int height;
    private final int weight;
}
