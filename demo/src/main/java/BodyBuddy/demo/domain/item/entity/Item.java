package BodyBuddy.demo.domain.item.entity;

import BodyBuddy.demo.global.common.commonEnum.ItemCategory;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;

	private Integer requiredLevel;

	@Column(nullable = false)
	private Float price;

	@Column(nullable = false)
	private String Type;

	//아이템 이미지 경로
	@Column(nullable = true) // 이미지 경로는 nullable 가능
	private String imagePath; // 아이템 이미지 경로


	@Column(nullable = false, updatable = false)
	// updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
	private LocalDateTime createdAt = LocalDateTime.now();

	// 아이템 구매 여부
	@Enumerated(EnumType.STRING)
	private ItemStatus status;

	// 아이템 카테고리
	@Enumerated(EnumType.STRING)
	private ItemCategory category;

	/**
	 * 아이템 구매 여부 -> 멤버아이템이면 구매한 아이템
	 * 구매하지 않은 아이템은 null 가능
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberItem_id", nullable = true)
	private MemberItem memberItem;

	// 특정 회원이 아이템을 소유하고 있는지 확인
	public boolean isOwnedByMember(Long memberId) {
		return this.memberItem.getAvatar().getMember().getId().equals(memberId);
	}

}