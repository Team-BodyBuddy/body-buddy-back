package BodyBuddy.demo.domain.Trainer.entity;

import BodyBuddy.demo.global.common.member.entity.Member;
import BodyBuddy.demo.global.common.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Getter @Setter
public class Trainer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name; // 트레이너 이름

  private int age; // 트레이너 나이

  private String portfolio; // 트레이너 포트폴리오

  private int badgeCount; // 뱃지 개수

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  private int weight;

  private int height;

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
