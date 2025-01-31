package BodyBuddy.demo.global.apiPayload.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BodyBuddyException extends RuntimeException {

	private final ErrorCode errorCode;
}