package BodyBuddy.demo.domain.gym.dto;

import BodyBuddy.demo.domain.gym.entity.Gym;

public record GymResponseDto(
	Long id,
	String name
) {
	public static GymResponseDto from(Gym gym) {
		return new GymResponseDto(gym.getId(), gym.getName());
	}
}
