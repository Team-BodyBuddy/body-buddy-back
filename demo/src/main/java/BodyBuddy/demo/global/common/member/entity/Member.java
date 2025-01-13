package BodyBuddy.demo.global.common.member.entity;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.inBody.entity.InBody;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.global.common.Gender;
import BodyBuddy.demo.global.common.point.entity.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
//접근 권한을 Protected로 설계해야 하는지 여부
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue                      // PK
    @Column(name = "member_id")              //
    private Long id;

    private String nickName;

    private String realName;

    private String email;

    private Long level;

    private Long exp; // 이거 용도가 경험치 였나?

    private Long height;

    private Long weight;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 총 포인트: 모든 포인트 내역의 합계를 관리
    @Column(nullable = false)
    private Long totalPoints = 0L;

    // 회원의 포인트 내역
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    // 회원이 소유한 아이템
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItem> items = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avatar> avatars = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InBody> inBodies = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItem> memberItems = new ArrayList<>();


}
