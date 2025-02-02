package BodyBuddy.demo.domain.badge.converter;

import BodyBuddy.demo.domain.badge.dto.BadgeResponseDto;
import BodyBuddy.demo.domain.badge.entity.Badge;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BadgeConverter {

    /**
     * 뱃지 엔티티를 DTO로 변환
     */
    public BadgeResponseDto convertToDto(Badge badge) {
        return BadgeResponseDto.builder()
                .id(badge.getId())
                .badgeName(badge.getBadgeName())
                .badgeDescription(badge.getBadgeDescription())
                .iconUrl(badge.getIconUrl())
                .build();
    }

    /**
     * 뱃지 엔티티 리스트를 DTO 리스트로 변환
     */
    public List<BadgeResponseDto> convertToDtoList(List<Badge> badges) {
        return badges.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
