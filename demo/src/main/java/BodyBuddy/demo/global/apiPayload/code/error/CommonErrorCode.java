package BodyBuddy.demo.global.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	// ✅ 공통 오류
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증이 필요합니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "403", "권한이 없습니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "404", "요청한 리소스를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 내부 오류가 발생했습니다."),

	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
