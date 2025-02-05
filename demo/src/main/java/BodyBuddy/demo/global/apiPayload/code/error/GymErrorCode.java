package BodyBuddy.demo.global.apiPayload.code.error;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GymErrorCode implements ErrorCode {

    GYM_NOT_FOUND(HttpStatus.NOT_FOUND, "GYM404", "유효하지 않은 헬스장입니다."),
    GYM_NOT_MATCH(HttpStatus.BAD_REQUEST, "GYM401", "회원은 해당 헬스장에 속해 있지 않습니다.")
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
