package BodyBuddy.demo.domain.trainer;

import BodyBuddy.demo.domain.gym.Gym;
import BodyBuddy.demo.domain.member.Member;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 트레이너 이름

    private String portfolio; // 트레이너 포트폴리오

    private int badgeCount; // 뱃지 개수

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    // 편의 메서드: 회원 추가
    public void addMember(Member member) {
        members.add(member);
        member.setTrainer(this);
    }

    // 편의 메서드: 회원 삭제
    public void removeMember(Member member) {
        members.remove(member);
        member.setTrainer(null);
    }

}