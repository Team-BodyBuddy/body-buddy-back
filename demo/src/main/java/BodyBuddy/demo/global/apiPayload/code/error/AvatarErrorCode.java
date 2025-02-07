package BodyBuddy.demo.global.apiPayload.code.error;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AvatarErrorCode implements ErrorCode {

    AVATAR_NOT_FOUND(HttpStatus.NOT_FOUND, "AVATAR404", "멤버Id에 따른 아바타를 찾지 못했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
