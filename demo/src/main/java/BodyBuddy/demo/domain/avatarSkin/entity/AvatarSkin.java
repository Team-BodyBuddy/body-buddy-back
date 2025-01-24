package BodyBuddy.demo.domain.avatarSkin.entity;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id", nullable = false)   //FK 컬럼 이름
	private Avatar avatar;

}