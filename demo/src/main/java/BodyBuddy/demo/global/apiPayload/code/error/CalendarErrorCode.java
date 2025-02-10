package BodyBuddy.demo.global.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarErrorCode implements ErrorCode {

  CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "CALENDAR404", "캘린더를 찾지 못했습니다."),
  CLASS_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND,"CALENDAR404","수업을 찾지 못했습니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}