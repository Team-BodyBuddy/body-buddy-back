package BodyBuddy.demo.domain.avatarSkin.entity;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import jakarta.persistence.*;
import lombok.*;

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
