package BodyBuddy.demo.global.common.point.domain;


import BodyBuddy.demo.global.common.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Point {

    @Id @GeneratedValue
    @Column(name="point_id",nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)   //FK 컬럼 이름
    private Member member;

    private Long amount;

    private String description;

    @Column(nullable = false, updatable = false)
    // updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
    private LocalDateTime createdAt = LocalDateTime.now();


    public void setMember(Member member) {
        this.member = member;
        member.getPoints().add(this);
    }

    //포인트 추가 로직

}
