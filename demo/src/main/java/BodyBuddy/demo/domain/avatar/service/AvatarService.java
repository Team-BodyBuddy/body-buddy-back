package BodyBuddy.demo.domain.avatar.service;


import BodyBuddy.demo.domain.avatar.dto.AvatarInfoRequestDTO;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.apiPayload.code.error.AvatarErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public Avatar createDefaultAvatar(Member member) {
        Avatar avatar = Avatar.builder()
            .level(1L)
            .exp(0L)
            .point(0L)
            .rankingScore(0L)
            .member(member)
            .build();
        return avatarRepository.save(avatar);
    }

    /**
     * 회원의 포인트 총합 조회
     */
    public Long getTotalPoints(Long memberId) {
        return avatarRepository.findByMemberId(memberId)
            .map(Avatar::getPoint)
            .orElseThrow(() -> new BodyBuddyException(AvatarErrorCode.AVATAR_NOT_FOUND));
    }

    /**
     * 회원의 기본 정보 조회
     */
    public AvatarInfoRequestDTO getAvatarInfoRequestDTO(Long memberId) {
        Avatar avatar = avatarRepository.findByMemberId(memberId)
            .orElseThrow(() -> new BodyBuddyException(AvatarErrorCode.AVATAR_NOT_FOUND));
        return AvatarInfoRequestDTO.from(avatar);
    }


}
