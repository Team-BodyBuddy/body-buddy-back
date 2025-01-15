package BodyBuddy.demo.domain.avatar.entity;

import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.global.common.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Avatar {

    @Id @GeneratedValue
    @Column(name = "avatar_id")
    private Long id;

    private Long level;

    private Long exp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)   //FK 컬럼 이름
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvatarSkin> avatarSkins = new ArrayList<>();
    //다대일 -> 일대다로 관계 변경

}
