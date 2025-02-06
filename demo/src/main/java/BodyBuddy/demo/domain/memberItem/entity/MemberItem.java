package BodyBuddy.demo.domain.memberItem.entity;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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
import java.util.ArrayList;
import java.util.List;
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
	private Long usedPoints;

	// 현재 아이템 상태 (true: 착용 중)
	private boolean isEquipped;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id", nullable = false)
	private Avatar avatar;

	@OneToMany(mappedBy = "memberItem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Item> items = new ArrayList<>();

	@Column(nullable = false, updatable = false)
	private LocalDateTime purchasedAt = LocalDateTime.now();

}