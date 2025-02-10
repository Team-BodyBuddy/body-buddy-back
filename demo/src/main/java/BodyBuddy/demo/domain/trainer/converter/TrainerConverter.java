package BodyBuddy.demo.domain.trainer.converter;

import BodyBuddy.demo.domain.badge.entity.Badge;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponse;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponseDto;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainerConverter {

    public TrainerResponse convertToTrainerResponse(Trainer trainer) {
        return TrainerResponse.builder()
                .id(trainer.getId())
                .realName(trainer.getRealName())
                .age(calculateAge(trainer.getBirthday()))
                .gender(trainer.getGender())
                .height(trainer.getHeight())
                .weight(trainer.getWeight())
                .region(trainer.getRegion())
                .gymName(trainer.getGym() != null ? trainer.getGym().getName() : "소속 없음")
                .badges(convertBadges(trainer.getBadges()))
                .portfolios(convertPortfolios(trainer.getPortfolios()))
                .members(convertMembers(trainer.getMembers()))
                .build();
    }

    private List<TrainerResponse.BadgeInfo> convertBadges(List<Badge> badges) {
        return badges.stream()
                .map(badge -> TrainerResponse.BadgeInfo.builder()
                        .id(badge.getId())
                        .badgeName(badge.getBadgeName())
                        .badgeDescription(badge.getBadgeDescription())
                        .iconUrl(badge.getIconUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TrainerResponse.PortfolioInfo> convertPortfolios(List<Portfolio> portfolios) {
        return portfolios.stream()
                .map(portfolio -> TrainerResponse.PortfolioInfo.builder()
                        .id(portfolio.getId())
                        .title(portfolio.getTitle())
                        .description(portfolio.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TrainerResponse.MemberInfo> convertMembers(List<Member> members) {
        return members.stream()
                .map(member -> TrainerResponse.MemberInfo.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 트레이너 엔티티를 TrainerResponseDto 리스트로 변환
     */
    public List<TrainerResponseDto> convertToTrainerDtoList(List<Trainer> trainers) {
        return trainers.stream()
                .map(this::convertToTrainerDto)
                .collect(Collectors.toList());
    }

    /**
     * 트레이너 엔티티를 TrainerResponseDto로 변환
     */
    public TrainerResponseDto convertToTrainerDto(Trainer trainer) {
        return TrainerResponseDto.builder()
                .id(trainer.getId())
                .realName(trainer.getRealName())
                .age(calculateAge(trainer.getBirthday()))
                .build();
    }

    /**
     * 생년월일을 기반으로 현재 만 나이 계산
     */
    private int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
