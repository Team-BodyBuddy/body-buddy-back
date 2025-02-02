package BodyBuddy.demo.domain.member.dto;

import java.time.LocalDate;

import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import BodyBuddy.demo.global.common.commonEnum.Region;

public record MyPageResponseDto(
	String nickname,
	Long avatarLevel,
	String realName,
	Gender gender,
	LocalDate birthday,
	Region region,
	String gymName,
	String heightWeight
) {
	public static MyPageResponseDto from(Member member) {
		String heightWeight = String.format("%.0fcm / %.0fkg", member.getHeight(), member.getWeight());
		return new MyPageResponseDto(
			member.getNickname(),
			member.getAvatar() != null ? member.getAvatar().getLevel() : 1,
			member.getRealName(),
			member.getGender(),
			member.getBirthday(),
			member.getRegion(),
			member.getGym()!= null ? member.getGym().getName() : "미선택",
			heightWeight
		);
	}
}
