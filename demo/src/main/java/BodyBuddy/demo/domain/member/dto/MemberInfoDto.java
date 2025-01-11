package BodyBuddy.demo.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDto {
    private Long id;          // 회원 ID
    private String nickname;  // 닉네임
    private int level;         // 아바타 레벨
}