package BodyBuddy.demo.domain.trainer.dto;

import BodyBuddy.demo.global.common.commonEnum.Gender;
import BodyBuddy.demo.global.common.commonEnum.Region;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TrainerResponse {
    private String realName;
    private int age;
    private Gender gender;
    private Float height;
    private Float weight;
    private Region region;
    private String gymName;
    private List<BadgeInfo> badges;
    private List<PortfolioInfo> portfolios;
    private List<MemberInfo> members;

    @Getter
    @Builder
    public static class BadgeInfo {
        private Long id;
        private String badgeName;
        private String badgeDescription;
        private String iconUrl;
    }

    @Getter
    @Builder
    public static class PortfolioInfo {
        private Long id;
        private String title;
        private String description;
    }

    @Getter
    @Builder
    public static class MemberInfo {
        private Long id;
        private String nickname;
    }
}
