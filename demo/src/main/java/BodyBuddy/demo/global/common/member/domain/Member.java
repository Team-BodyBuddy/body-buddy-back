package BodyBuddy.demo.global.common.member.domain;

import BodyBuddy.demo.domain.avatar.domain.Avatar;
import BodyBuddy.demo.domain.inBody.domain.InBody;
import BodyBuddy.demo.domain.memberItem.domain.MemberItem;
import BodyBuddy.demo.global.common.Gender;
import BodyBuddy.demo.global.common.point.domain.Point;
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

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    //부모 엔티티 상태변화 -> 자식 엔티티도 변동
    //부모 엔티티 삭제 -> 자식 엔티티도 같이 삭제
    private List<Point> points = new ArrayList<>();

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
