package BodyBuddy.demo.domain.portfolio.controller;

import BodyBuddy.demo.domain.portfolio.dto.PortfolioRequest;
import BodyBuddy.demo.domain.portfolio.dto.PortfolioResponse;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.domain.portfolio.service.PortfolioService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainers/{trainerId}/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * 트레이너 포트폴리오 등록
     */
    @PostMapping
    public ApiResponse<Void> createPortfolio(@PathVariable Long trainerId, @RequestBody PortfolioRequest request) {
        portfolioService.createPortfolio(trainerId, request);
        return ApiResponse.of(SuccessStatus.PORTFOLIO_CREATED, null);
    }

    /**
     * 트레이너 포트폴리오 수정
     */
    @PutMapping("/{portfolioId}")
    public ApiResponse<Void> updatePortfolio(
            @PathVariable Long trainerId,
            @PathVariable Long portfolioId,
            @RequestBody PortfolioRequest request) {

        portfolioService.updatePortfolio(trainerId, portfolioId, request);
        return ApiResponse.of(SuccessStatus.PORTFOLIO_UPDATED, null);
    }
}
