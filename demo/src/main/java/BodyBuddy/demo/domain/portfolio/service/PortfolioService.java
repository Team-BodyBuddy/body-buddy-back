package BodyBuddy.demo.domain.portfolio.service;

import BodyBuddy.demo.domain.portfolio.converter.PortfolioConverter;
import BodyBuddy.demo.domain.portfolio.dto.PortfolioRequest;
import BodyBuddy.demo.domain.portfolio.dto.PortfolioResponse;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.domain.portfolio.repository.PortfolioRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TrainerRepository trainerRepository;
    private final PortfolioConverter portfolioConverter;

    /**
     * 트레이너 포트폴리오 등록
     */
    @Transactional
    public void createPortfolio(Long trainerId, PortfolioRequest request) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));

        Portfolio portfolio = portfolioConverter.toEntity(request, trainer);
        portfolioRepository.save(portfolio);
    }

    /**
     * 트레이너 포트폴리오 수정
     */
    @Transactional
    public void updatePortfolio(Long trainerId, Long portfolioId, PortfolioRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));

        if (!portfolio.getTrainer().getId().equals(trainerId)) {
            throw new IllegalStateException("포트폴리오 수정 권한이 없습니다.");
        }

        portfolioConverter.updatePortfolio(portfolio, request);
    }
}
