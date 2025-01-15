package BodyBuddy.demo.domain.item.entity;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import BodyBuddy.demo.global.common.Category;
import BodyBuddy.demo.global.common.ItemStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.stream.Collectors;
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

    @Column(nullable = false)
    private Float price;

    // 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    // 아이템 상태 ( ACTIVE/INACTIVE )
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    //아이템 이미지 경로
    @Column(nullable = true) // 이미지 경로는 nullable 가능
    private String imagePath; // 아이템 이미지 경로

    //아이템 생성 시점
    @Column(nullable = false, updatable = false)
    // updatable = false -> update 쿼리 실행 시 createdAt필드 변경 X
    private LocalDateTime createdAt = LocalDateTime.now();

    // 멤버 아이템 - 아이템에 종속됨
    // 아이템 삭제되면 멤버 아이템도 삭제
    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItem> memberItems = new ArrayList<>();

}
