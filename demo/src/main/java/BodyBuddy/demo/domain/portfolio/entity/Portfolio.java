package BodyBuddy.demo.domain.portfolio.entity;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Portfolio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
