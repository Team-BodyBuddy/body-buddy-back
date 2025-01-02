package BodyBuddy.demo.trainer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Trainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int level;
}
