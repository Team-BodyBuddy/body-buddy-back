package BodyBuddy.demo.global.apiPayload.code.error;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TrainerErrorCode implements ErrorCode {

    TRAINER_NOT_FOUND(HttpStatus.NOT_FOUND, "TRAINER_404", "해당 트레이너를 찾을 수 없습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
