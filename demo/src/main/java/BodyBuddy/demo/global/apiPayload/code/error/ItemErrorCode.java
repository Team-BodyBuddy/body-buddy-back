package BodyBuddy.demo.global.apiPayload.code.error;

import BodyBuddy.demo.global.apiPayload.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {

  ITEMS_NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM4001", "멤버Id에 따른 ITEM 찾지 못했습니다."),
  INVALID_CATEGORY(HttpStatus.NOT_FOUND, "ITEM4002", "멤버Id에 따른 ItemCategory 찾지 못했습니다.")
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}