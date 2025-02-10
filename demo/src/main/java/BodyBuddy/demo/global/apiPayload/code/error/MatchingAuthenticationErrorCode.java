package BodyBuddy.demo.global.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchingAuthenticationErrorCode implements ErrorCode {
	DUPLICATE_AUTH_REQUEST(HttpStatus.BAD_REQUEST, "MATCHING_404", "이미 인증 요청이 진행 중입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
