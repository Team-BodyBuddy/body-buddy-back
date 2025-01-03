package BodyBuddy.demo.domain.item.domain;

import BodyBuddy.demo.domain.memberItem.domain.MemberItem;
import BodyBuddy.demo.global.common.point.domain.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private String description;

    private Integer required_level;

    private Float price;

    @Column(nullable = false, updatable = false)
    // updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItem> memberItems = new ArrayList<>();
}
