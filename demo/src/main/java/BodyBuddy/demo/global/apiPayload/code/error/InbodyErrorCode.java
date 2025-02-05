package BodyBuddy.demo.global.apiPayload.code.error;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InbodyErrorCode implements ErrorCode {

    INBODY_SIZE_ERROR(HttpStatus.BAD_REQUEST, "INBODY403", "랭크 점수 갱신하기에 분석할 수 있는 인바디 데이터 수가 2개 미만입니다."),
    INBODY_NOT_FOUND(HttpStatus.NOT_FOUND, "INBODY404", "해당 회원의 인바디 데이터가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
