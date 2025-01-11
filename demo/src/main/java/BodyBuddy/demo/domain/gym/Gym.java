package BodyBuddy.demo.domain.gym;

import BodyBuddy.demo.domain.region.Region;
import BodyBuddy.demo.domain.trainer.Trainer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Gym")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체육관 고유 ID

    @Column(nullable = false)
    private String name; // 체육관 이름

    @Column(nullable = false)
    private String address; // 체육관 주소

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region; // 소속 지역

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trainer> trainers; // 해당 체육관의 트레이너 리스트
}
