package BodyBuddy.demo.global.common.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

  private Long id;
  private String nickName;
  private Long level;
  private Long exp;

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Response{
    private Long id;
    private String nickName;
    private String profileImage;
    private Long level;
    private Long exp;
  }

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MemberInquiry{
    private Long id;
    private String realName;
    private String profileImage;
    private Long level;
  }


}
