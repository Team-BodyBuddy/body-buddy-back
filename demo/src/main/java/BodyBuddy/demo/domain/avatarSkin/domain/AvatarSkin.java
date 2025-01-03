package BodyBuddy.demo.domain.avatarSkin.domain;

import BodyBuddy.demo.domain.avatar.domain.Avatar;
import BodyBuddy.demo.domain.memberItem.domain.MemberItem;
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
public class AvatarSkin {

    @Id @GeneratedValue
    @Column(name = "avatarSkin_id")
    private Long id;

    private String name;

    private Long minLevel;

    private Long maxLevel;

    private String imagePath;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id", nullable = false)   //FK 컬럼 이름
    private Avatar avatar;

}
