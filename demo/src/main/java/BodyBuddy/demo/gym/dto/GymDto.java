package BodyBuddy.demo.gym.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 체육관 데이터 전달 객체 (GymDto)
 * 클라이언트와의 데이터 전송에 사용
 */
@Data
@Builder
public class GymDto {
    private Long id;       // 체육관 고유 ID
    private String name;   // 체육관 이름
    private String address; // 체육관 주소
}
