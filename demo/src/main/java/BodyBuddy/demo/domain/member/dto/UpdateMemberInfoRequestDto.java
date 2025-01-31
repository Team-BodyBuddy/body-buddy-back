package BodyBuddy.demo.domain.member.dto;

import BodyBuddy.demo.global.common.commonEnum.Region;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberInfoRequestDto(
	@NotNull Region region,
	@NotNull Float height,
	@NotNull Float weight,
	Long gymId
) {
}