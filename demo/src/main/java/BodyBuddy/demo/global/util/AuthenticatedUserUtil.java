package BodyBuddy.demo.global.util;

import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.error.CommonErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserUtil {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    /**
     * 현재 로그인한 사용자의 username(loginId) 반환
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BodyBuddyException(CommonErrorCode.UNAUTHORIZED);
        }
        return authentication.getName(); // username (loginId)
    }

    /**
     * 현재 로그인한 사용자가 Member인지 Trainer인지 확인 후 반환
     */
    public Object getCurrentUser() {
        String username = getCurrentUsername();

        Member member = memberRepository.findByLoginId(username).orElse(null);
        if (member != null) {
            return member;
        }

        Trainer trainer = trainerRepository.findByLoginId(username).orElse(null);
        if (trainer != null) {
            return trainer;
        }

        throw new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID);
    }

    /**
     * 현재 로그인한 사용자가 Member라면 Member 반환
     */
    public Member getCurrentMember() {
        String username = getCurrentUsername();
        return memberRepository.findByLoginId(username)
            .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
    }

    /**
     * 현재 로그인한 사용자가 Trainer라면 Trainer 반환
     */
    public Trainer getCurrentTrainer() {
        String username = getCurrentUsername();
        return trainerRepository.findByLoginId(username)
            .orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));
    }
}
