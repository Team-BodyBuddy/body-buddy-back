package BodyBuddy.demo.domain.matchingAuthentication.entity;

import java.time.LocalDateTime;

import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.common.commonEnum.AuthenticationRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MatchingAuthentication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member; // 요청을 보낸 회원

	@ManyToOne(fetch = FetchType.LAZY)
	private Trainer trainer; // 요청을 받은 트레이너

	@Enumerated(EnumType.STRING)
	private AuthenticationRequest status; // 요청 상태 (PENDING, ACCEPTED, REJECTED)

	private LocalDateTime requestDate; // 요청 날짜

	public void accept() {
		this.status = AuthenticationRequest.ACCEPTED;
		this.member.setTrainer(this.trainer); // ★ Member-trainer 관계 세팅
	}
}