package BodyBuddy.demo.domain.trainer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import BodyBuddy.demo.global.common.commonEnum.Region;

public record UpdateTrainerProfileDto(
    @NotNull
    @Schema(description = "헬스장 ID", example = "1")
    Long gymId,

    @NotNull
    @Schema(description = "서울 지역구", example = "GANGNAM")
    Region region,

    @Positive
    @Schema(description = "키(cm)", example = "180.5")
    Float height,

    @Positive
    @Schema(description = "몸무게(kg)", example = "75.0")
    Float weight
) {}