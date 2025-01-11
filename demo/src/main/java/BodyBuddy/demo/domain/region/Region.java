package BodyBuddy.demo.domain.region;

import BodyBuddy.demo.domain.gym.Gym;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Region")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 지역 고유 ID

    @Column(nullable = false)
    private String name; // 지역 이름

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gym> gyms; // 해당 지역에 속한 체육관 리스트
}
