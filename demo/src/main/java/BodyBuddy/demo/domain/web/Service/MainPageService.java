package BodyBuddy.demo.domain.web.Service;

import BodyBuddy.demo.domain.avatar.DTO.AvatarDTO;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.avatarSkin.repository.AvatarSkinRepository;
import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.domain.inBody.DTO.InBodyDTO;
import BodyBuddy.demo.domain.inBody.entity.InBody;
import BodyBuddy.demo.domain.inBody.repository.InBodyRepository;
import BodyBuddy.demo.global.common.InBodyStatus;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import BodyBuddy.demo.global.common.member.entity.Member;
import BodyBuddy.demo.global.common.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.point.DTO.PointDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainPageService {

  private final MemberRepository memberRepository;
  private final AvatarRepository avatarRepository;
  private final AvatarSkinRepository avatarSkinRepository;
  private final InBodyRepository inBodyRepository;

  /**
   * 포인트 총합 조회 로직
   */
  public PointDTO.Response getTotalPoints(Long memberId) {
    // 멤버 조회
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다."));

    // PointDTO 생성 및 반환
    return PointDTO.Response.builder()
        .amount(member.getTotalPoints())
        .build();
  }

  /**
   * 멤버 정보 조회 로직
   */
  public MemberDTO getMemberInfo(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다."));
    return new MemberDTO(member.getId(), member.getNickName(), member.getLevel(), member.getExp());
  }

  /**
   * 아바타 정보 조회 로직
   */
  public AvatarDTO getLatestAvatarInfo(Long memberId) {
    // 1. 멤버 조회
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다."));

    // 2. 멤버와 연결된 아바타 조회
    Avatar avatar = member.getAvatars().stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("멤버에게 아바타가 없습니다."));

    // 3. 아바타와 연결된 최신 스킨 조회
    AvatarSkin latestSkin = avatarSkinRepository.findTopByAvatarIdOrderByIdDesc(avatar.getId())
        .orElseThrow(() -> new IllegalArgumentException("아바타 스킨이 없습니다"));

    // 4. DTO 생성 및 반환
    return new AvatarDTO(
        avatar.getId(),
        latestSkin.getImagePath(),
        new AvatarDTO.Response(latestSkin.getId(), latestSkin.getName(), latestSkin.getImagePath())
    );
  }

  /**
   * 최근 2개의 인바디 데이터를 비교하여 체중, 근육량, 체지방률의 변화를 반환
   */
  public InBodyDTO.RecentComparison getRecentComparison(Long memberId) {
    if (!memberRepository.existsById(memberId)) {
      throw new IllegalArgumentException("멤버가 없습니다.");
    }

    List<InBody> inBodies = inBodyRepository.findTop2ByMemberIdOrderByCreatedAtDesc(memberId);
    if (inBodies.size() < 2) {
      throw new IllegalArgumentException("인바디 데이터 부족");
    }

    InBody current = inBodies.get(0);
    InBody previous = inBodies.get(1);

    return new InBodyDTO.RecentComparison(
        current.getWeight(), previous.getWeight(),
        current.getWeight() - previous.getWeight(),
        InBodyStatus.compare(current.getWeight(), previous.getWeight()),

        current.getMuscle(), previous.getMuscle(),
        current.getMuscle() - previous.getMuscle(),
        InBodyStatus.compare(current.getMuscle(), previous.getMuscle()),

        current.getBodyFat(), previous.getBodyFat(),
        current.getBodyFat() - previous.getBodyFat(),
        InBodyStatus.compare(current.getBodyFat(), previous.getBodyFat())
    );
  }

  /**
   * 멤버의 최신 5개의 체중 기록을 반환
   */
  public List<InBodyDTO.WeightHistory> getWeightHistory(Long memberId) {
    if (!memberRepository.existsById(memberId)) {
      throw new IllegalArgumentException("Member not found");
    }

    // 최신 5개의 체중 기록만 반환
    return inBodyRepository.findTop5ByMemberIdOrderByCreatedAtDesc(memberId)
        .stream()
        .map(inBody -> new InBodyDTO.WeightHistory(
            inBody.getCreatedAt().toLocalDate(),
            inBody.getWeight()
        ))
        .collect(Collectors.toList());
  }
}
