package BodyBuddy.demo.domain.member.service;

import BodyBuddy.demo.domain.avatar.service.AvatarService;
import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.service.GymService;
import BodyBuddy.demo.domain.member.converter.MemberConverter;
import BodyBuddy.demo.domain.member.dto.SignUpRequestDto;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final GymService gymService;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AvatarService avatarService;

    /**
     * 일반 회원(Member) 회원가입
     */
    public void signupMember(SignUpRequestDto.MemberSignupRequest request) {

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BodyBuddyException(MemberErrorCode.NOT_MATCH_CONFIRMEDPASS);
        }

        // ID 및 닉네임 중복 체크
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new BodyBuddyException(MemberErrorCode.DUPLICATE_ID);
        }
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new BodyBuddyException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        // 헬스장 검증 및 DTO → 엔티티 변환
        Gym gym = gymService.validateGym(request.getGymId());
        Member member = memberConverter.toMemberEntity(request, gym);

        // 비밀번호 암호화 및 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // 회원 저장
        Member savedMember = memberRepository.save(member);

        // 아바타 생성 및 초기값 설정
        avatarService.createDefaultAvatar(savedMember);
    }

    /**
     * 트레이너(Trainer) 회원가입
     */
    public void signupTrainer(SignUpRequestDto.TrainerSignupRequest request) {

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BodyBuddyException(MemberErrorCode.NOT_MATCH_CONFIRMEDPASS);
        }

        // ID 중복 체크
        if (trainerRepository.existsByLoginId(request.getLoginId())) {
            throw new BodyBuddyException(MemberErrorCode.DUPLICATE_ID);
        }

        // 헬스장 검증 및 DTO → 엔티티 변환
        Gym gym = gymService.validateGym(request.getGymId());
        Trainer trainer = memberConverter.toTrainerEntity(request, gym);

        // 비밀번호 암호화 및 저장
        trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));
        trainerRepository.save(trainer);
    }

    /**
     * 로그인 처리 (일반 회원(Member) 및 트레이너(Trainer) 구분)
     */
    public String login(SignUpRequestDto.MemberLoginRequest request) {
        // Member 조회
        Member member = memberRepository.findByLoginId(request.getLoginId()).orElse(null);

        // Trainer 조회
        Trainer trainer = trainerRepository.findByLoginId(request.getLoginId()).orElse(null);

        // 일반 회원 로그인 처리
        if (member != null) {
            if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
                throw new BodyBuddyException(MemberErrorCode.NOT_MATCH_PASSWORD);
            }
            return jwtTokenProvider.createToken(member.getLoginId(), "ROLE_MEMBER");
        }

        // 트레이너 로그인 처리
        if (trainer != null) {
            if (!passwordEncoder.matches(request.getPassword(), trainer.getPassword())) {
                throw new BodyBuddyException(MemberErrorCode.NOT_MATCH_PASSWORD);
            }
            return jwtTokenProvider.createToken(trainer.getLoginId(), "ROLE_TRAINER");
        }

        // 사용자 정보가 없을 경우 예외 처리
        throw new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID);
    }

    /**
     * 회원 탈퇴 (일반 회원 및 트레이너 구분)
     */
    public void deleteUser(String loginId) {
        // 일반 회원(Member) 탈퇴
        if (memberRepository.existsByLoginId(loginId)) {
            Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));
            memberRepository.delete(member);
            return;
        }

        // 트레이너(Trainer) 탈퇴
        if (trainerRepository.existsByLoginId(loginId)) {
            Trainer trainer = trainerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));
            trainerRepository.delete(trainer);
            return;
        }

        throw new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID);
    }

    /**
     * 회원 정보 조회
     */

    public String getNicknameByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
            .map(Member::getNickname)
            .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
    }

}
