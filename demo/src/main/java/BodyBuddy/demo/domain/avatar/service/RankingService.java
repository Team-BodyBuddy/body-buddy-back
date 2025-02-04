package BodyBuddy.demo.domain.avatar.service;

import BodyBuddy.demo.domain.avatar.converter.RankingConverter;
import BodyBuddy.demo.domain.avatar.dto.RankingResponse;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.domain.gym.service.GymService;
import BodyBuddy.demo.global.apiPayload.code.error.CommonErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final AvatarRepository avatarRepository;
    private final GymService gymService;
    private final RankingConverter rankingConverter;

    public Page<RankingResponse.GlobalRanking> getGlobalRankings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rankingScore").descending());
        Page<Avatar> avatars = avatarRepository.findAllByOrderByRankingScoreDesc(pageable);

        return avatars.map(avatar -> rankingConverter.convertToGlobalRanking(avatar,
            avatars.getContent().indexOf(avatar) + 1 + (page * size)));
    }

    public Page<RankingResponse.GlobalRanking> getGymRankings(Long gymId, int page, int size) {
        Gym gym = gymService.validateGym(gymId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("rankingScore").descending());
        Page<Avatar> avatars = avatarRepository.findByMemberGymOrderByRankingScoreDesc(gym,
            pageable);

        return avatars.map(avatar -> rankingConverter.convertToGlobalRanking(avatar,
            avatars.getContent().indexOf(avatar) + 1 + (page * size)));
    }

    public RankingResponse.GlobalRanking getUserGlobalRanking(Long memberId) {
        Avatar avatar = avatarRepository.findByMemberId(memberId)
            .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));
        int rank = avatarRepository.findGlobalRankForMember(memberId);
        return rankingConverter.convertToGlobalRanking(avatar, rank);
    }

    public RankingResponse.GlobalRanking getUserGymRanking(Long memberId, Long gymId) {
        Avatar avatar = avatarRepository.findByMemberId(memberId)
            .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.MEMBER_NOT_FOUND));
        Gym gym = gymService.validateGym(gymId);

        if (!avatar.getMember().getGym().equals(gym)) {
            throw new BodyBuddyException(CommonErrorCode.GYM_NOT_MATCH);
        }

        int rank = avatarRepository.findGymRankForMember(memberId, gymId);
        return rankingConverter.convertToGlobalRanking(avatar, rank);
    }
}
