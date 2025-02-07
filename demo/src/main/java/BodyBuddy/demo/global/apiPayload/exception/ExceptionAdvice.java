package BodyBuddy.demo.global.apiPayload.exception;

import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.error.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * BodyBuddyException 처리
     */
    @ExceptionHandler(BodyBuddyException.class)
    public ResponseEntity<ApiResponse<Void>> handleBodyBuddyException(BodyBuddyException e) {
        log.error("BodyBuddyException 발생: {}", e.getErrorCode().getMessage());
        return ResponseEntity
            .status(e.getErrorCode().getHttpStatus())
            .body(ApiResponse.onFailure(
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                null
            ));
    }

    /**
     * GeneralException 처리
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException e) {
        log.error("GeneralException 발생: {}", e.getErrorCode().getMessage());
        return ResponseEntity
            .status(e.getErrorCode().getHttpStatus())
            .body(ApiResponse.onFailure(
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                null
            ));
    }

    /**
     * 그 외 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unexpected Exception 발생: ", e);
        return ResponseEntity
            .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
            .body(ApiResponse.onFailure(
                CommonErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                null
            ));
    }
}
