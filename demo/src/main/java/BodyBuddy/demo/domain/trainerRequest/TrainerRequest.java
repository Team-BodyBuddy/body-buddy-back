package BodyBuddy.demo.domain.trainerRequest;

import BodyBuddy.demo.domain.member.Member;
import BodyBuddy.demo.domain.trainer.Trainer;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 요청을 보낸 회원

    @ManyToOne(fetch = FetchType.LAZY)
    private Trainer trainer; // 요청을 받은 트레이너

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // 요청 상태 (PENDING, ACCEPTED, REJECTED)

    private LocalDateTime requestDate; // 요청 날짜
}
