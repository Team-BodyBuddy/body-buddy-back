package BodyBuddy.demo.domain.portfolio.controller;

import BodyBuddy.demo.domain.portfolio.dto.PortfolioRequest;
import BodyBuddy.demo.domain.portfolio.dto.PortfolioResponse;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.domain.portfolio.service.PortfolioService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainers/{trainerId}/portfolios")
@Tag(name = "트레이너 포트폴리오 관리", description = "트레이너 포트폴리오 등록 및 수정")
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * 트레이너 포트폴리오 등록
     */
    @Operation(summary = "포트폴리오 등록", description = "트레이너가 포트폴리오를 등록합니다.")
    @PostMapping
    public ApiResponse<Void> createPortfolio(@PathVariable Long trainerId, @RequestBody PortfolioRequest request) {
        portfolioService.createPortfolio(trainerId, request);
        return ApiResponse.of(SuccessStatus.PORTFOLIO_CREATED, null);
    }

    /**
     * 트레이너 포트폴리오 수정
     */
    @Operation(summary = "포트폴리오 수정", description = "트레이너가 자신의 포트폴리오를 수정합니다.")
    @PutMapping("/{portfolioId}")
    public ApiResponse<Void> updatePortfolio(
            @PathVariable Long trainerId,
            @PathVariable Long portfolioId,
            @RequestBody PortfolioRequest request) {

        portfolioService.updatePortfolio(trainerId, portfolioId, request);
        return ApiResponse.of(SuccessStatus.PORTFOLIO_UPDATED, null);
    }
}
