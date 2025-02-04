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

	// 체육관 관련 오류
	GYM_NOT_FOUND(HttpStatus.NOT_FOUND, "GYM404", "유효하지 않은 헬스장입니다."),
	GYM_NOT_MATCH(HttpStatus.BAD_REQUEST, "GYM401", "회원은 해당 헬스장에 속해 있지 않습니다."),

	// 포트폴리오 관련 오류
	PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLTFOLIO404", "포트폴리오를 찾을 수 없습니다."),
	PORTFOLIO_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "FOLTFOLIO403", "포트폴리오 수정권한이 없습니다."),

	//인바디 관련 오류
	INBODY_SIZE_ERROR(HttpStatus.BAD_REQUEST, "INBODY403", "랭크 점수 갱신하기에 분석할 수 있는 인바디 데이터 수가 2개 미만입니다."),
	INBODY_NOT_FOUND(HttpStatus.NOT_FOUND, "INBODY404", "해당 회원의 인바디 데이터가 존재하지 않습니다."),

	//아바타 관련 오류
	AVATAR_NOT_FOUND(HttpStatus.NOT_FOUND, "AVATAR404", "멤버Id에 따른 아바타를 찾지 못했습니다.")
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
