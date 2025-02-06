package BodyBuddy.demo.domain.avatarSkin.entity;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvatarSkin {

	@Id
	@GeneratedValue
	@Column(name = "avatarSkin_id")
	private Long id;

	private String name;

	private Long minLevel;

	private Long maxLevel;

	private String imagePath;

	// 최신 스킨 확인용
	private LocalDateTime acquiredAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id", nullable =true)   //FK 컬럼 이름
	private Avatar avatar;

}