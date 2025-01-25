package BodyBuddy.demo.domain.memberItem.entity;

import java.time.LocalDateTime;

import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberItem {

	@Id
	@GeneratedValue
	@Column(name = "memberItem_id")
	private Long id;

	//사용된 포인트
	private Float usedPoints;

	// 구매 당시의 상태
	@Enumerated(EnumType.STRING)
	private ItemStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)   //FK 컬럼 이름
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)   //FK 컬럼 이름
	private Item item;

	@Column(nullable = false, updatable = false)
	private LocalDateTime purchasedAt = LocalDateTime.now();

}