package BodyBuddy.demo.domain.inbody.service;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.inbody.converter.InBodyConverter;
import BodyBuddy.demo.domain.inbody.dto.InBodyToRankingScoreDTO;
import BodyBuddy.demo.domain.inbody.entity.InBody;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.inbody.repository.InBodyRepository;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InBodyToRankingScoreService {

    private final InBodyRepository inBodyRepository;
    private final AvatarRepository avatarRepository;

    @Transactional
    public InBodyToRankingScoreDTO calculateAndSaveRankingScore(Long memberId) {
        List<InBody> inBodyData = inBodyRepository.findTop2ByMemberIdOrderByCreatedAtDesc(memberId);

        if (inBodyData.size() < 2) {
            throw new IllegalArgumentException("랭크 점수 갱신하기에 분석할 수 있는 인바디 데이터 수가 2개 미만입니다.");
        }

        InBody current = inBodyData.get(0);
        InBody previous = inBodyData.get(1);

        int score = calculateRankingScore(current, previous, current.getMember().getGender());

        Avatar avatar = avatarRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("멤버Id에 따른 아바타를 찾지 못했습니다.: " + memberId));

        avatar.updateRankingScore(score);
        avatarRepository.save(avatar);

        return InBodyConverter.toRankingScoreDTO(inBodyData, score);
    }

    private int calculateRankingScore(InBody current, InBody previous, Gender gender) {
        float weightChange = current.getWeight() - previous.getWeight();
        float muscleChange = current.getMuscle() - previous.getMuscle();
        float bodyFatChange = current.getBodyFat() - previous.getBodyFat();

        int muscleScore = calculateMuscleScore(current.getMuscle(), gender, current.getMember().getHeight());
        int bodyFatScore = calculateBodyFatScore(current.getBodyFat(), gender, current.getMember().getHeight());

        return Math.round(muscleScore + bodyFatScore + weightChange * 2); // 정수로 반환
    }

    private int calculateMuscleScore(float muscle, Gender gender, float height) {
        float[] standards = getMuscleStandards((int) height, gender); // 키를 정수로 변환
        float standard = standards[1];

        if (muscle < standard * 0.85) return 100;
        if (muscle < standard) return Math.round(100 * 1.3f);
        if (muscle <= standard * 1.15) return Math.round(100 * 1.7f);
        if (muscle <= standard * 1.3) return 200;
        return 300;
    }

    private int calculateBodyFatScore(float bodyFat, Gender gender, float height) {
        float[] standards = getBodyFatStandards((int) height, gender); // 키를 정수로 변환
        float standard = standards[1];

        if (bodyFat < standard * 0.85) return Math.round(30 * 3f);
        if (bodyFat < standard) return Math.round(30 * 2f);
        if (bodyFat <= standard * 1.15) return Math.round(30 * 1.7f);
        if (bodyFat <= standard * 1.3) return Math.round(30 * 1.3f);
        return 30;
    }

    private float[] getMuscleStandards(int height, Gender gender) {
        return switch (height) {
            case 155 -> gender == Gender.MALE ? new float[]{19, 22} : new float[]{18, 22};
            case 160 -> gender == Gender.MALE ? new float[]{21, 24} : new float[]{19, 24};
            case 165 -> gender == Gender.MALE ? new float[]{23, 26} : new float[]{20, 25};
            case 170 -> gender == Gender.MALE ? new float[]{24, 27} : new float[]{21, 26};
            case 175 -> gender == Gender.MALE ? new float[]{25, 28} : new float[]{22, 27};
            case 180 -> gender == Gender.MALE ? new float[]{26, 29} : new float[]{23, 28};
            case 185 -> gender == Gender.MALE ? new float[]{27, 30} : new float[]{24, 29};
            default -> new float[]{0, 0};
        };
    }

    private float[] getBodyFatStandards(int height, Gender gender) {
        return switch (height) {
            case 155 -> gender == Gender.MALE ? new float[]{27, 31} : new float[]{15, 20};
            case 160 -> gender == Gender.MALE ? new float[]{31, 35} : new float[]{17, 22};
            case 165 -> gender == Gender.MALE ? new float[]{33, 37} : new float[]{19, 24};
            case 170 -> gender == Gender.MALE ? new float[]{35, 39} : new float[]{21, 26};
            case 175 -> gender == Gender.MALE ? new float[]{37, 41} : new float[]{23, 28};
            case 180 -> gender == Gender.MALE ? new float[]{39, 43} : new float[]{25, 30};
            case 185 -> gender == Gender.MALE ? new float[]{41, 45} : new float[]{27, 32};
            default -> new float[]{0, 0};
        };
    }
}
