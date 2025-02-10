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
import BodyBuddy.demo.global.apiPayload.code.error.GymErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MatchingAuthenticationErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
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
			.orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));

		return MyPageResponseDto.from(member);
	}


	/**
	 * 회원 정보 수정 (지역 및 GYM 선택 포함)
	 */
	public MyPageResponseDto updateMemberInfo(Long memberId, UpdateMemberInfoRequestDto requestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));

		member.setRegion(requestDto.region());
		member.setHeight(requestDto.height());
		member.setWeight(requestDto.weight());

		if (requestDto.gymId() != null) {
			Gym gym = gymRepository.findById(requestDto.gymId())
				.orElseThrow(() -> new BodyBuddyException(GymErrorCode.GYM_NOT_FOUND));
			member.setGym(gym);
		}

		return MyPageResponseDto.from(member);
	}
	/**
	 * 트레이너 인증 요청
	 */
	public void requestTrainerAuthentication(Long memberId, String uuId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));

		Trainer trainer = trainerRepository.findByLoginId(uuId)
			.orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));

		matchingAuthenticationRepository
			.findByMemberIdAndTrainerIdAndStatus(member.getId(), trainer.getId(), AuthenticationRequest.PENDING)
			.ifPresent(existing -> {
				throw new BodyBuddyException(MatchingAuthenticationErrorCode.DUPLICATE_AUTH_REQUEST);
			});

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
