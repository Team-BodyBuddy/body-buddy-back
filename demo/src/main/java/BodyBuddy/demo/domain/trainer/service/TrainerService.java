package BodyBuddy.demo.domain.trainer.service;

import BodyBuddy.demo.domain.matchingAuthentication.repository.MatchingAuthenticationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.trainer.dto.TrainerMyPageResponseDto;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.domain.trainer.converter.TrainerConverter;
import BodyBuddy.demo.domain.trainer.dto.TrainerResponse;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.matchingAuthentication.repository.MatchingAuthenticationRepository;
import BodyBuddy.demo.domain.trainer.dto.TrainerMyPageResponseDto;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.common.commonEnum.AuthenticationRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerService {

	private final TrainerRepository trainerRepository;
	private final MatchingAuthenticationRepository matchingAuthenticationRepository;
  private final TrainerRepository trainerRepository;
  private final TrainerConverter trainerConverter;

    public TrainerResponse getTrainerDetails(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));
        return trainerConverter.convertToTrainerResponse(trainer);
    }

	/**
	 * 트레이너 마이페이지 정보 조회
	 */
	@Transactional(readOnly = true)
	public TrainerMyPageResponseDto getTrainerMyPage(Long trainerId) {
		Trainer trainer = trainerRepository.findById(trainerId)
			.orElseThrow(() -> new IllegalArgumentException("트레이너가 존재하지 않습니다."));

		long pendingRequestCount = matchingAuthenticationRepository.countByTrainerAndStatus(trainer, AuthenticationRequest.PENDING);

		return TrainerMyPageResponseDto.from(trainer, pendingRequestCount);
	}

	/**
	 * 트레이너 프로필 이미지 수정
	 */
	public void updateProfileImage(Long trainerId, String newProfileImageUrl) {
		Trainer trainer = trainerRepository.findById(trainerId)
			.orElseThrow(() -> new IllegalArgumentException("트레이너가 존재하지 않습니다."));

		trainer.updateProfileImage(newProfileImageUrl);
		trainerRepository.save(trainer);
	}
}
