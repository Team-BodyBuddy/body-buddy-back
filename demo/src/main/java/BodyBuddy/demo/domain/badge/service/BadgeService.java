package BodyBuddy.demo.domain.badge.service;


import BodyBuddy.demo.domain.badge.entity.Badge;
import BodyBuddy.demo.domain.badge.repository.BadgeRepository;
import BodyBuddy.demo.domain.inbody.entity.InBody;
import BodyBuddy.demo.domain.inbody.repository.InBodyRepository;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final InBodyRepository inBodyRepository;

    /**
     * 트레이너 조건에 따라 뱃지 확인 및 추가
     */
    public List<Badge> checkAndAssignBadges(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.TRAINER_NOT_FOUND));

        List<Badge> newBadges = new ArrayList<>();
        
        // 1. 누적 회원 수 기준
        Badge memberBadge = checkCumulativeMembersBadge(trainerId);
        if (memberBadge != null) {
            memberBadge.assignTrainer(trainer);
            badgeRepository.save(memberBadge);
            newBadges.add(memberBadge);
        }

        // 2. 다이어트 메이커 기준
        Badge dietBadge = checkDietMakerBadge(trainerId);
        if (dietBadge != null) {
            dietBadge.assignTrainer(trainer);
            badgeRepository.save(dietBadge);
            newBadges.add(dietBadge);
        }

        // 3. 근육 마스터 기준
        Badge muscleBadge = checkMuscleMasterBadge(trainerId);
        if (muscleBadge != null) {
            muscleBadge.assignTrainer(trainer);
            badgeRepository.save(muscleBadge);
            newBadges.add(muscleBadge);
        }

        return newBadges;
    }

    /**
     * 누적 회원 수 기준 뱃지 확인
     */
    private Badge checkCumulativeMembersBadge(Long trainerId) {
        int memberCount = memberRepository.countByTrainerId(trainerId);
        String iconUrl = null;

        if (memberCount >= 200) {
            iconUrl = "https://example.com/icons/200.png";
            return new Badge("200명 달성", "누적 인원 200명 달성", iconUrl, null);
        } else if (memberCount >= 100) {
            iconUrl = "https://example.com/icons/100.png";
            return new Badge("100명 달성", "누적 인원 100명 달성", iconUrl, null);
        } else if (memberCount >= 50) {
            iconUrl = "https://example.com/icons/50.png";
            return new Badge("50명 달성", "누적 인원 50명 달성", iconUrl, null);
        } else if (memberCount >= 25) {
            iconUrl = "https://example.com/icons/25.png";
            return new Badge("25명 달성", "누적 인원 25명 달성", iconUrl, null);
        } else if (memberCount >= 10) {
            iconUrl = "https://example.com/icons/10.png";
            return new Badge("10명 달성", "누적 인원 10명 달성", iconUrl, null);
        } else if (memberCount >= 5) {
            iconUrl = "https://example.com/icons/5.png";
            return new Badge("5명 달성", "누적 인원 5명 달성", iconUrl, null);
        }

        return null;
    }

    /**
     * 다이어트 메이커 기준 뱃지 확인
     */
    private Badge checkDietMakerBadge(Long trainerId) {
        List<Member> members = memberRepository.findByTrainerId(trainerId);

        for (Member member : members) {
            List<InBody> inBodies = inBodyRepository.findByMemberIdOrderByCreatedAtAsc(member.getId());
            if (inBodies.size() < 2) continue; // 데이터가 부족한 경우

            float initialWeight = inBodies.get(0).getWeight();
            float latestWeight = inBodies.get(inBodies.size() - 1).getWeight();
            float weightLoss = initialWeight - latestWeight;

            if (weightLoss >= 20) {
                String iconUrl = "https://example.com/icons/diet20.png";
                return new Badge("다이어트 메이커 (20kg)", "체중 20kg 감소 달성", iconUrl, null);
            } else if (weightLoss >= 10) {
                String iconUrl = "https://example.com/icons/diet10.png";
                return new Badge("다이어트 메이커 (10kg)", "체중 10kg 감소 달성", iconUrl, null);
            }
        }
        return null;
    }

    /**
     * 근육 마스터 기준 뱃지 확인
     */
    private Badge checkMuscleMasterBadge(Long trainerId) {
        List<Member> members = memberRepository.findByTrainerId(trainerId);

        for (Member member : members) {
            List<InBody> inBodies = inBodyRepository.findByMemberIdOrderByCreatedAtDesc(member.getId());
            if (inBodies.isEmpty()) continue; // 데이터가 없는 경우

            InBody latestInBody = inBodies.get(0);
            float muscleMass = latestInBody.getMuscle();
            float standardMuscleMass = calculateStandardMuscleMass(member.getHeight(), member.getGender());

            if (muscleMass >= standardMuscleMass) {
                String iconUrl = "https://example.com/icons/muscle.png";
                return new Badge("근육 마스터", "골격근량 표준 이상 증가", iconUrl, null);
            }
        }
        return null;
    }

    /**
     * 키에 따른 표준 골격근량 계산
     */
    private float calculateStandardMuscleMass(float height, Gender gender) {
        if (gender == Gender.MALE) {
            if (height <= 155) return 20.5f; // (19 + 22) / 2
            else if (height <= 160) return 22.5f; // (21 + 24) / 2
            else if (height <= 165) return 24.5f; // (23 + 26) / 2
            else if (height <= 170) return 25.5f; // (24 + 27) / 2
            else if (height <= 175) return 26.5f; // (25 + 28) / 2
            else if (height <= 180) return 27.5f; // (26 + 29) / 2
            else if (height <= 185) return 28.5f; // (27 + 30) / 2
            else return 28.5f; // 최대값 이상일 때
        } else if (gender == Gender.FEMALE) {
            if (height <= 155) return 20f; // (18 + 22) / 2
            else if (height <= 160) return 21.5f; // (19 + 24) / 2
            else if (height <= 165) return 22.5f; // (20 + 25) / 2
            else if (height <= 170) return 23.5f; // (21 + 26) / 2
            else if (height <= 175) return 24.5f; // (22 + 27) / 2
            else if (height <= 180) return 25.5f; // (23 + 28) / 2
            else if (height <= 185) return 26.5f; // (24 + 29) / 2
            else return 26.5f; // 최대값 이상일 때
        }
        throw new BodyBuddyException(MemberErrorCode.GENDER_NOT_FOUND);
    }

}
