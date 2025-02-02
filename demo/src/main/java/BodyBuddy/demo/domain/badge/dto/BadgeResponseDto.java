package BodyBuddy.demo.domain.badge.dto;

import lombok.Builder;

@Builder
public record BadgeResponseDto(
        Long id,
        String badgeName,
        String badgeDescription,
        String iconUrl
) {}
