package BodyBuddy.demo.domain.memberItem.entity;

import BodyBuddy.demo.domain.item.entity.Item;
import BodyBuddy.demo.global.common.ItemStatus;
import BodyBuddy.demo.global.common.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
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

    //아이템 구매시점
    @Column(nullable = false, updatable = false)
    private LocalDateTime purchasedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}