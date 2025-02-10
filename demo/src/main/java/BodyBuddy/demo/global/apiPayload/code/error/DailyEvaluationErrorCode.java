package BodyBuddy.demo.global.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum DailyEvaluationErrorCode implements ErrorCode{

	DAILY_EVALUATION_ERROR_CODE(HttpStatus.NOT_FOUND,"DAILY404","오늘의 평가가 없습니다");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}
