package BodyBuddy.demo.domain.avatar.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.domain.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {

	@Id
	@GeneratedValue
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


}