package BodyBuddy.demo.domain.member;

import BodyBuddy.demo.domain.member.memberEnum.Gender;
import BodyBuddy.demo.domain.trainer.Trainer;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter //바꿀 예정
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private int level; // 아바타 레벨
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String region;
    private String gym;
    private int height; // 키 (cm)
    private int weight; // 몸무게 (kg)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}