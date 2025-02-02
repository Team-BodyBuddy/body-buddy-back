package BodyBuddy.demo.domain.matchingAuthentication.service;

import BodyBuddy.demo.domain.matchingAuthentication.repository.MatchingAuthenticationRepository;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.trainer.dto.TrainerRequestDto;
import BodyBuddy.demo.domain.matchingAuthentication.entity.MatchingAuthentication;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.common.commonEnum.AuthenticationRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingAuthenticationService {

	private final MatchingAuthenticationRepository matchingAuthenticationRepository;
	private final TrainerRepository trainerRepository;
	private final MemberRepository memberRepository;

	/**
	 * 트레이너 인증 요청 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<TrainerRequestDto> getRequestList(Long trainerId) {
		Trainer trainer = trainerRepository.findById(trainerId)
			.orElseThrow(() -> new IllegalArgumentException("트레이너가 존재하지 않습니다."));

		return matchingAuthenticationRepository.findByTrainerAndStatus(trainer, AuthenticationRequest.PENDING)
			.stream()
			.map(TrainerRequestDto::from)
			.toList();
	}

	/**
	 * 트레이너 인증 요청 수락
	 */
	public void acceptRequest(Long trainerId, Long requestId) {
		MatchingAuthentication request = matchingAuthenticationRepository.findById(requestId)
			.orElseThrow(() -> new IllegalArgumentException("요청이 존재하지 않습니다."));

		if (!request.getTrainer().getId().equals(trainerId)) {
			throw new IllegalArgumentException("요청이 해당 트레이너의 것이 아닙니다.");
		}

		// 요청 수락 처리
		request.accept();
		matchingAuthenticationRepository.save(request);
	}

	/**
	 * 트레이너 인증 요청 거절
	 */
	public void rejectRequest(Long trainerId, Long requestId) {
		MatchingAuthentication request = matchingAuthenticationRepository.findById(requestId)
			.orElseThrow(() -> new IllegalArgumentException("요청이 존재하지 않습니다."));

		if (!request.getTrainer().getId().equals(trainerId)) {
			throw new IllegalArgumentException("요청이 해당 트레이너의 것이 아닙니다.");
		}

		// 요청 거절 처리
		matchingAuthenticationRepository.delete(request);
	}
}

