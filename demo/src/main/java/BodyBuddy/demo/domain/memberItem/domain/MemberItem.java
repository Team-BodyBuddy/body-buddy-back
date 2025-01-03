package BodyBuddy.demo.domain.memberItem.domain;

import BodyBuddy.demo.domain.item.domain.Item;
import BodyBuddy.demo.global.common.member.domain.Member;
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

    @Id @GeneratedValue
    @Column(name = "memberItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)   //FK 컬럼 이름
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)   //FK 컬럼 이름
    private Item item;
    //이것도 다대일 아니라 일대다 같은데 아님 말구

    @Column(nullable = false, updatable = false)
    // updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
    private LocalDateTime purchasedAt = LocalDateTime.now();

}
