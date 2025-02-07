package BodyBuddy.demo.global.apiPayload.exception;

import BodyBuddy.demo.global.apiPayload.code.BaseErrorCode;
import BodyBuddy.demo.global.apiPayload.code.ErrorReasonDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 회원 관련 예외
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_001", "해당 회원을 찾을 수 없습니다."),
    DUPLICATE_ID(HttpStatus.BAD_REQUEST, "MEMBER_002", "이미 사용 중인 ID입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER_003", "이미 사용 중인 닉네임입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_401", "유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_402", "리프레시 토큰이 만료되었습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_004", "비밀번호가 일치하지 않습니다."),

    // 서버 내부 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_500", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
            .httpStatus(httpStatus)
            .isSuccess(false)
            .code(code)
            .message(message)
            .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return getReason();
    }
}
