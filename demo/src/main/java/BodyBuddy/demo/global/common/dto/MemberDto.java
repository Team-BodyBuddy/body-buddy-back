package BodyBuddy.demo.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 전달 객체 (MemberDto)
 * 클라이언트와 데이터 전송 시 사용
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;        // 회원 ID
    private String nickname; // 닉네임
    private int level;      // 회원 레벨
    private float height;   // 키 (cm)
    private float weight;   // 몸무게 (kg)
    private Long gymId;     // 소속 체육관 ID
}
