package BodyBuddy.demo.domain.item.DTO;

import BodyBuddy.demo.global.common.commonEnum.ItemStatus;

public record CategoryItemDTO(
    Long id,
    String name,
    String imagePath,
    Long price,
    // 상태만 전달 (ACTIVE / INACTIVE)
    ItemStatus status
) {}
