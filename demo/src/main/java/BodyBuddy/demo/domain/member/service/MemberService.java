package BodyBuddy.demo.domain.member.service;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.repository.GymRepository;
import BodyBuddy.demo.domain.member.converter.MemberConverter;
import BodyBuddy.demo.domain.member.dto.SignUpRequestDto;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final GymRepository gymRepository;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 일반 회원(Member) 회원가입
     */
    public void signupMember(SignUpRequestDto.MemberSignupRequest request) {

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // ID 및 닉네임 중복 체크
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 ID입니다.");
        }
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 헬스장 검증 및 DTO → 엔티티 변환
        Gym gym = gymRepository.findById(request.getGymId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 헬스장입니다."));
        Member member = memberConverter.toMemberEntity(request, gym);

        // 비밀번호 암호화 및 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    /**
     * 트레이너(Trainer) 회원가입
     */
    public void signupTrainer(SignUpRequestDto.TrainerSignupRequest request) {

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // ID 중복 체크
        if (trainerRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 ID입니다.");
        }

        // 헬스장 검증 및 DTO → 엔티티 변환
        Gym gym = gymRepository.findById(request.getGymId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 헬스장입니다."));
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
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            return jwtTokenProvider.createToken(member.getLoginId(), "ROLE_MEMBER");
        }

        // 트레이너 로그인 처리
        if (trainer != null) {
            if (!passwordEncoder.matches(request.getPassword(), trainer.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            return jwtTokenProvider.createToken(trainer.getLoginId(), "ROLE_TRAINER");
        }

        // 사용자 정보가 없을 경우 예외 처리
        throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }

    /**
     * 로그아웃 처리 (서버 작업 없음)
     */
    public void logout() {
        // 클라이언트에서 JWT 삭제 처리
    }

    /**
     * 회원 탈퇴 (일반 회원 및 트레이너 구분)
     */
    public void deleteUser(String loginId) {
        // 일반 회원(Member) 탈퇴
        if (memberRepository.existsByLoginId(loginId)) {
            Member member = memberRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            memberRepository.delete(member);
            return;
        }

        // 트레이너(Trainer) 탈퇴
        if (trainerRepository.existsByLoginId(loginId)) {
            Trainer trainer = trainerRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 트레이너입니다."));
            trainerRepository.delete(trainer);
            return;
        }

        throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    }
}
