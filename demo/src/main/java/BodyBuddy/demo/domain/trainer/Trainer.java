package BodyBuddy.demo.domain.trainer;

import BodyBuddy.demo.domain.member.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

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
