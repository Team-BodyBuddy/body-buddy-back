package BodyBuddy.demo.global.common.dto;

import lombok.Data;

/**
 * 회원 생성 요청 DTO (MemberRequestDto)
 * 클라이언트가 회원 생성 요청 시 사용
 */
@Data
public class MemberRequestDto {
    private String nickname; // 닉네임
    private int level;       // 초기 레벨
    private float height;    // 키 (cm)
    private float weight;    // 몸무게 (kg)
    private Long gymId;      // 소속 체육관 ID
}

