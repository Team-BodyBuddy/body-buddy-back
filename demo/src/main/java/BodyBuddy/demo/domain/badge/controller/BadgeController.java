package BodyBuddy.demo.domain.badge.controller;

import BodyBuddy.demo.domain.badge.converter.BadgeConverter;
import BodyBuddy.demo.domain.badge.dto.BadgeResponseDto;
import BodyBuddy.demo.domain.badge.entity.Badge;
import BodyBuddy.demo.domain.badge.service.BadgeService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/badges")
public class BadgeController {

    private final BadgeService badgeService;
    private final BadgeConverter badgeConverter;

    /**
     * 특정 트레이너의 뱃지 리스트 조회
     */
    @GetMapping("/trainers/{trainerId}")
    public ApiResponse<List<BadgeResponseDto>> getTrainerBadges(@PathVariable Long trainerId) {
        List<Badge> badges = badgeService.checkAndAssignBadges(trainerId);
        List<BadgeResponseDto> badgeDtos = badgeConverter.convertToDtoList(badges);
        return ApiResponse.of(SuccessStatus.BADGE_SUCCESS, badgeDtos);
    }
}
