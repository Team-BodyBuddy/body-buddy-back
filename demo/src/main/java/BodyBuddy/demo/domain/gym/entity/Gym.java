package BodyBuddy.demo.domain.gym.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 체육관 엔티티 (Gym)
 * 체육관의 기본 정보를 관리
 */
@Entity
@Table(name = "Gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체육관 고유 ID

    private String name;    // 체육관 이름
    private String address; // 체육관 주소
}
