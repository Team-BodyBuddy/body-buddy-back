package BodyBuddy.demo.domain.portfolio.converter;

import BodyBuddy.demo.domain.portfolio.dto.PortfolioRequest;
import BodyBuddy.demo.domain.portfolio.dto.PortfolioResponse;
import BodyBuddy.demo.domain.portfolio.entity.Portfolio;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public class PortfolioConverter {

    public Portfolio toEntity(PortfolioRequest request, Trainer trainer) {
        return Portfolio.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .trainer(trainer)
                .build();
    }

    public void updatePortfolio(Portfolio portfolio, PortfolioRequest request) {
        portfolio.update(request.getTitle(), request.getDescription());
    }

    public PortfolioResponse toResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .title(portfolio.getTitle())
                .description(portfolio.getDescription())
                .build();
    }
}
