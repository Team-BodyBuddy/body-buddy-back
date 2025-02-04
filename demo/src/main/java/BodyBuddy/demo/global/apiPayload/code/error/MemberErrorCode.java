package BodyBuddy.demo.global.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

	//예시입니다.

	// ✅ 회원 관련 오류
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "해당 회원을 찾을 수 없습니다."),
	TRAINER_NOT_FOUND(HttpStatus.NOT_FOUND, "TRAINER_404", "해당 트레이너를 찾을 수 없습니다."),
	NOT_FOUND_ID(HttpStatus.NOT_FOUND, "ID_404", "존재하지 않는 사용자입니다."),
	DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER_002", "이미 사용 중인 닉네임입니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER_003", "이미 가입된 이메일입니다."),
	NOT_MATCH_CONFIRMEDPASS(HttpStatus.BAD_REQUEST, "MEMBER_004", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
	NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_005", "비밀번호가 일치하지 않습니다."),
	DUPLICATE_ID(HttpStatus.BAD_REQUEST, "MEMBER_006", "이미 사용 중인 ID입니다."),
	GENDER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_007", "잘못된 성별입니다.")
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}