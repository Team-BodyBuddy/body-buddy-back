package BodyBuddy.demo.domain.func.mainPage.service;

import BodyBuddy.demo.domain.avatar.domain.Avatar;
import BodyBuddy.demo.domain.avatarSkin.domain.AvatarSkin;
import BodyBuddy.demo.domain.avatarSkin.repository.AvatarSkinRepository;
import BodyBuddy.demo.domain.func.mainPage.DTO.AvatarDTO;
import BodyBuddy.demo.domain.func.mainPage.DTO.WeightChangeDTO;
import BodyBuddy.demo.domain.inBody.domain.InBody;
import BodyBuddy.demo.domain.inBody.repository.InBodyRepository;
import BodyBuddy.demo.domain.func.mainPage.DTO.InBodyDTO;
import BodyBuddy.demo.domain.func.mainPage.DTO.MainPageDTO;
import BodyBuddy.demo.global.common.member.domain.Member;
import BodyBuddy.demo.global.common.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.point.repository.PointRepository;
import BodyBuddy.demo.global.common.point.domain.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MainPageService {

    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final InBodyRepository inBodyRepository;

    public MainPageService(MemberRepository memberRepository, PointRepository pointRepository,
        InBodyRepository inBodyRepository) {
        this.memberRepository = memberRepository;
        this.pointRepository = pointRepository;
        this.inBodyRepository = inBodyRepository;
    }

    /**
     * 메인 페이지 데이터 조회
     */
    public MainPageDTO getMainPage(Long memberId) {
        // 회원 정보 조회
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 포인트 총합 계산
        Long totalPoints = getTotalPoints(memberId);

        // 최신 + 직전 인바디 정보 조회 및 체중 증감 계산
        Map<String, Object> inBodyData = getRecentAndPreviousInBody(memberId);
        InBodyDTO recentInBody = (InBodyDTO) inBodyData.get("recentInBody");
        String weightChange = (String) inBodyData.get("weightChange");

        // 아바타 정보 조회
        AvatarDTO avatar = getAvatarInfo(memberId);

        // 체중 변화 기록 조회
        List<WeightChangeDTO> weightHistory = getWeightHistory(memberId);

        // MainPageDTO 생성
        return MainPageDTO.builder()
            .nickName(member.getNickName())
            .points(totalPoints)
            .recentInBody(recentInBody)
            .weightChange(weightChange)
            .avatar(avatar)
            .weightHistory(weightHistory)
            .build();
    }

    /**
     * 포인트 총량 계산
     */
    public Long getTotalPoints(Long memberId) {
        return pointRepository.findByMemberId(memberId).stream()
            .mapToLong(Point::getAmount)
            .sum();
    }

    /**
     * 아바타 정보 조회
     */
    public AvatarDTO getAvatarInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Avatar avatar = member.getAvatars().stream().findFirst()
            .orElseThrow(() -> new IllegalArgumentException("아바타 정보가 없습니다."));

        List<String> skins = avatar.getAvatarSkins().stream()
            .map(AvatarSkin::getName)
            .toList();

        return AvatarDTO.builder()
            .imagePath("/default/image/path") // 실제 이미지 경로로 대체 가능
            .level(avatar.getLevel())
            .exp(avatar.getExp())
            .skins(skins)
            .build();
    }

    /**
     * 최신 + 직전 인바디 정보 조회 및 체중 증감 계산
     */
    public Map<String, Object> getRecentAndPreviousInBody(Long memberId) {
        List<InBody> inBodies = inBodyRepository.findTop2ByMemberIdOrderByCreatedAtDesc(memberId);

        if (inBodies.isEmpty()) {
            throw new IllegalArgumentException("인바디 정보가 없습니다.");
        }

        InBody recent = inBodies.get(0);
        InBody previous = inBodies.size() > 1 ? inBodies.get(1) : null;

        InBodyDTO recentDTO = new InBodyDTO(recent.getWeight(), recent.getMuscle(), recent.getBodyFat());
        String weightChange = "same";

        if (previous != null) {
            if (recent.getWeight() > previous.getWeight()) {
                weightChange = "increase";
            } else if (recent.getWeight() < previous.getWeight()) {
                weightChange = "decrease";
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("recentInBody", recentDTO);
        result.put("weightChange", weightChange);

        return result;
    }

    /**
     * 체중 변화 조회
     */
    public List<WeightChangeDTO> getWeightHistory(Long memberId) {
        return inBodyRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId).stream()
            .limit(5)
            .map(inBody -> WeightChangeDTO.builder()
                .weight(inBody.getWeight())
                .date(inBody.getCreatedAt())
                .build())
            .toList();
    }
}