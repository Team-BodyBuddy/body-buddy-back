package BodyBuddy.demo.domain.trainer.dto;

import java.time.LocalDate;
import java.time.Period;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.common.commonEnum.Gender;


public record TrainerMyPageResponseDto(
	Long trainerId,
	String nameAndAge, // 이름(XX세, 성별)
	String uuid,  // ID: xxx
	String gymName,    // 소속 GYM 이름
	String profilePicture, // 프로필 이미지 경로
	long pendingRequestCount // 요청 대기 개수
) {
	public static TrainerMyPageResponseDto from(Trainer trainer, long pendingRequestCount) {
		int age = Period.between(trainer.getBirthday(), LocalDate.now()).getYears();
		String nameAndAge = String.format("%s(%d세, %s)", trainer.getRealName(), age, trainer.getGender().name());
		String uuid = "ID: " + trainer.getUuid();

		return new TrainerMyPageResponseDto(
			trainer.getId(),
			nameAndAge,
			uuid,
			trainer.getGym() != null ? trainer.getGym().getName() : "미소속",
			trainer.getProfileImageUrl(),
			pendingRequestCount
		);
	}
}

