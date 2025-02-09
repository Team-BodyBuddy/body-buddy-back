package BodyBuddy.demo.domain.member.service;

import BodyBuddy.demo.domain.matchingAuthentication.repository.MatchingAuthenticationRepository;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.domain.matchingAuthentication.entity.MatchingAuthentication;
import BodyBuddy.demo.domain.matchingAuthentication.repository.MatchingAuthenticationRepository;
import BodyBuddy.demo.domain.member.dto.MyPageResponseDto;
import BodyBuddy.demo.domain.member.dto.UpdateMemberInfoRequestDto;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.common.commonEnum.AuthenticationRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberMyPageService {

	private final MemberRepository memberRepository;
	private final TrainerRepository trainerRepository;
	private final GymRepository gymRepository;
	private final MatchingAuthenticationRepository matchingAuthenticationRepository;

	/**
	 * 마이페이지 정보 조회
	 */
	@Transactional(readOnly = true)
	public MyPageResponseDto getMyPage(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		return MyPageResponseDto.from(member);
	}


	/**
	 * 회원 정보 수정 (지역 및 GYM 선택 포함)
	 */
	public MyPageResponseDto updateMemberInfo(Long memberId, UpdateMemberInfoRequestDto requestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		member.setRegion(requestDto.region());
		member.setHeight(requestDto.height());
		member.setWeight(requestDto.weight());

		if (requestDto.gymId() != null) {
			Gym gym = gymRepository.findById(requestDto.gymId())
				.orElseThrow(() -> new IllegalArgumentException("GYM이 존재하지 않습니다."));
			member.setGym(gym);
		}

		return MyPageResponseDto.from(member);
	}
	/**
	 * 트레이너 인증 요청
	 */
	public void requestTrainerAuthentication(Long memberId, String uuId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

		Trainer trainer = trainerRepository.findByLoginId(uuId)
			.orElseThrow(() -> new IllegalArgumentException("트레이너가 존재하지 않습니다."));

		MatchingAuthentication authentication = new MatchingAuthentication(
			null,
			member,
			trainer,
			AuthenticationRequest.PENDING,
			LocalDateTime.now()
		);
		matchingAuthenticationRepository.save(authentication);
	}
}
