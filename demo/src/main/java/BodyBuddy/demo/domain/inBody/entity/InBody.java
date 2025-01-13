package BodyBuddy.demo.domain.inBody.entity;


import BodyBuddy.demo.global.common.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InBody {

    @Id
    @GeneratedValue
    @Column(name = "inBody_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)   //FK 컬럼 이름
    private Member member;

    private Float weight;

    private Float muscle;

    private Float bodyFat;

    @Column(nullable = false, updatable = false)
    // updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
    private LocalDateTime createdAt = LocalDateTime.now();

}
