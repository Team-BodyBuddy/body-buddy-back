package BodyBuddy.demo.domain.avatar.service;

import BodyBuddy.demo.domain.avatar.dto.AvatarInfoResponseDTO;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.domain.avatarSkin.repository.AvatarSkinRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.global.apiPayload.code.error.AvatarErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final AvatarSkinRepository avatarSkinRepository;

    // 회원 아바타 조회 메서드
    private Avatar findAvatarByMemberId(Long memberId) {
        return avatarRepository.findByMemberId(memberId)
            .orElseThrow(() -> new BodyBuddyException(AvatarErrorCode.AVATAR_NOT_FOUND));
    }

    /**
     * 회원의 포인트 총합 조회
     */
    public Long getTotalPoints(Long memberId) {
        return avatarRepository.findPointByMemberId(memberId)
            .orElseThrow(() -> new BodyBuddyException(AvatarErrorCode.AVATAR_NOT_FOUND));
    }

    /**
     * 회원의 기본 정보 조회
     */
    public AvatarInfoResponseDTO getAvatarInfoRequestDTO(Long memberId) {
        Avatar avatar = findAvatarByMemberId(memberId);

        return AvatarInfoResponseDTO.from(avatar);
    }

    /**
     * 회원 가입 후 기본 아바타 생성 및 초기 스킨 자동 장착
     */
    @Transactional
    public Avatar createDefaultAvatar(Member member) {
        AvatarSkin defaultSkin = avatarSkinRepository
            .findFirstByMinLevelLessThanEqualAndMaxLevelGreaterThanEqual(0L, 0L)
            .orElseThrow(() -> new IllegalStateException("기본 스킨이 존재하지 않습니다."));

        Avatar avatar = Avatar.builder()
            .level(1L)  // 기본 레벨 0
            .exp(0L)
            .point(0L)
            .rankingScore(0L)
            .member(member)
            .avatarSkin(defaultSkin)  // 초기 스킨 자동 장착
            .build();

        return avatarRepository.save(avatar);
    }

    /**
     * 아바타의 레벨을 업데이트하면서, 새로운 스킨 자동 장착
     */
    @Transactional
    public Avatar updateAvatarLevel(Long memberId, Long newLevel) {
        Avatar avatar = findAvatarByMemberId(memberId);

        avatar.updateLevelAndSkin(newLevel, avatarSkinRepository);
        return avatarRepository.save(avatar);
    }

}
