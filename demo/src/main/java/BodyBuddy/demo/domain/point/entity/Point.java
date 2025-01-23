package BodyBuddy.demo.domain.point.entity;

import java.time.LocalDateTime;

import BodyBuddy.demo.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Point {

	@Id
	@GeneratedValue
	@Column(name="point_id",nullable=false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private long amount;

	private String description;

	@Column(nullable = false, updatable = false)
	// updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
	private LocalDateTime createdAt = LocalDateTime.now();


	public void setMember(Member member) {
		this.member = member;
		member.getPoints().add(this);
	}

	public void setAmount(Long amount) {
		if (this.member != null) {
			this.member.setTotalPoints(
				this.member.getTotalPoints() - this.amount + amount
			);
		}
		this.amount = amount;
	}

}