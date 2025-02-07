package BodyBuddy.demo.global.apiPayload.code.error;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PortfolioErrorCode implements ErrorCode {

    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLTFOLIO404", "포트폴리오를 찾을 수 없습니다."),
    PORTFOLIO_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "FOLTFOLIO403", "포트폴리오 수정권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
