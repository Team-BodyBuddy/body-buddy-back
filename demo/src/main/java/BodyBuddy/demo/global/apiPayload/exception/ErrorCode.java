package BodyBuddy.demo.global.apiPayload.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	/**
	 * HTTP 상태 코드 반환
	 */
	HttpStatus getHttpStatus();

	/**
	 * 에러 코드 반환 (예: MEMBER_001)
	 */
	String getCode();

	/**
	 * 에러 메시지 반환 (예: "회원을 찾을 수 없습니다.")
	 */
	String getMessage();
}