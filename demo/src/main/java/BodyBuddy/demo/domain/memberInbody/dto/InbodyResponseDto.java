package BodyBuddy.demo.domain.memberInbody.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InbodyResponseDto {
    private String lastUploadDate; // 마지막 등록 날짜
    private boolean isValid;       // 유효성 여부
    private String message;        // 메시지 (오류 메시지)
}
