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

		//아바타 스킨 가져오기
		AvatarSkin avatarSkin = member.getAvatar().getAvatarSkin();

		return new TrainerRequestDto(
				request.getId(),
				member.getRealName(),
				age,
				member.getGender(),
				avatarSkin != null ? avatarSkin.getImagePath() : null, // 스킨이 없을 수도 있으므로 체크
				member.getBirthday()
		);
	}
}

