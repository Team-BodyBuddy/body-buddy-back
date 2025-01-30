package BodyBuddy.demo.domain.trainer.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.domain.matchingAuthentication.entity.MatchingAuthentication;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.common.commonEnum.Gender;

public record TrainerRequestDto(
	Long requestId,
	String memberName,
	int age,
	Gender gender,
	String avatarPicture,
	LocalDate birthday
) {
	public static TrainerRequestDto from(MatchingAuthentication request) {
		Member member = request.getMember();
		int age = Period.between(member.getBirthday(), LocalDate.now()).getYears();
		List<AvatarSkin> avatarSkins = member.getAvatar().getAvatarSkins();
		AvatarSkin mostRecentAvatarSkin = avatarSkins.get(avatarSkins.size() - 1);

		return new TrainerRequestDto(
			request.getId(),
			member.getRealName(),
			age,
			member.getGender(),
			mostRecentAvatarSkin.getImagePath(),
			member.getBirthday()
		);
	}
}

