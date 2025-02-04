package BodyBuddy.demo.domain.portfolio.service;

import BodyBuddy.demo.domain.portfolio.converter.PortfolioConverter;
import BodyBuddy.demo.domain.portfolio.dto.PortfolioRequest;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.domain.portfolio.repository.PortfolioRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.error.CommonErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
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
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.TRAINER_NOT_FOUND));

        Portfolio portfolio = portfolioConverter.toEntity(request, trainer);
        portfolioRepository.save(portfolio);
    }

    /**
     * 트레이너 포트폴리오 수정
     */
    @Transactional
    public void updatePortfolio(Long trainerId, Long portfolioId, PortfolioRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new BodyBuddyException(CommonErrorCode.PORTFOLIO_NOT_FOUND));

        if (!portfolio.getTrainer().getId().equals(trainerId)) {
            throw new BodyBuddyException(CommonErrorCode.PORTFOLIO_UNAUTHORIZED);
        }

        portfolioConverter.updatePortfolio(portfolio, request);
    }
}
