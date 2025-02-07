package BodyBuddy.demo.domain.avatar.entity;

import BodyBuddy.demo.domain.avatarSkin.repository.AvatarSkinRepository;
import BodyBuddy.demo.domain.memberItem.entity.MemberItem;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
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
	@Column(name = "avatar_id")
	private Long id;

	private Long level;

	private Long exp;

	private Long point;

	private Long rankingScore; // 나의 랭킹 점수


	@OneToOne(fetch = FetchType.LAZY)
	@MapsId // Avatar와 Member의 아이디를 동일하게 설정
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_skin_id")
	private AvatarSkin avatarSkin;

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

	// 아바타 레벨 변경시 스킨 자동 업데이트 메서드
	public void updateLevelAndSkin(Long newLevel, AvatarSkinRepository avatarSkinRepository) {
		this.level = newLevel;

		//10 단위로 범위 지정 (0~10, 11~20, 21~30, 31~40, 41~50)
		Long minRange = (newLevel / 10) * 10; // 10 단위 범위의 시작 값
		Long maxRange = minRange + 10; // 10 단위 범위의 끝 값

		this.avatarSkin = avatarSkinRepository.findFirstByMinLevelLessThanEqualAndMaxLevelGreaterThanEqual(minRange, maxRange)
				.orElseThrow(() -> new IllegalStateException("해당 레벨에 맞는 스킨이 존재하지 않습니다."));
	}

}