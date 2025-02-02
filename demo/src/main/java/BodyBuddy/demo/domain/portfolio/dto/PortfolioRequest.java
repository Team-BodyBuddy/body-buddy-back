package BodyBuddy.demo.domain.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PortfolioRequest {
    private String title;
    private String description;
}
