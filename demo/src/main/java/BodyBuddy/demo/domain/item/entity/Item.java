package BodyBuddy.demo.domain.item.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.global.common.commonEnum.ItemStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	private Integer required_level;

	@Enumerated(EnumType.STRING)
	private ItemStatus status;

	@Column(nullable = false)
	private Float price;

	//아이템 이미지 경로
	@Column(nullable = true) // 이미지 경로는 nullable 가능
	private String imagePath; // 아이템 이미지 경로


	@Column(nullable = false, updatable = false)
	// updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
	private LocalDateTime createdAt = LocalDateTime.now();


	@JsonIgnore
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberItem> memberItems = new ArrayList<>();

	// 특정 회원이 아이템을 소유하고 있는지 확인
	public boolean isOwnedByMember(Long memberId) {
		return memberItems.stream()
			.anyMatch(memberItem -> memberItem.getMember().getId().equals(memberId));
	}

}