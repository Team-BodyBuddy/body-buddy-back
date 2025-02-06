package BodyBuddy.demo.domain.avatar.entity;

import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
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
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

	private Long point;

	private Long rankingScore; // 나의 랭킹 점수


	@OneToOne(fetch = FetchType.LAZY)
	@MapsId // This ensures Avatar ID is the same as Member ID
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@JsonIgnore
	@OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AvatarSkin> avatarSkins = new ArrayList<>();

	public void addExp(long amount) {
		this.exp += amount;
	}

	public void addPoint(long amount) {
		this.point += amount;
	}

	public void addRankingScore(long amount) {
		this.rankingScore += amount;
	}

	public void updateRankingScore(int additionalScore) {
		this.rankingScore += additionalScore;
	}

	public void updatePointsAndExp(int additionalPoints, int additionalExp) {
		this.point = (this.point == null ? 0 : this.point) + additionalPoints;
		this.exp = (this.exp == null ? 0 : this.exp) + additionalExp;
	}

	// 랭킹 스코어를 설정하는 메서드
	public void setRankingScore(Long rankingScore) {
		this.rankingScore = rankingScore;
	}

	// 장착 중인 스킨
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatarSkin_id", nullable = false)
	private AvatarSkin avatarSkin;

	// 장착 중인 아이템
	@OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberItem> wearingItems;

	// 포인트 차감 메서드
	public void usePoints(Long amount) {
		if (this.point < amount) {
			throw new IllegalStateException("포인트가 부족합니다.");
		}
		this.point -= amount;
	}

}