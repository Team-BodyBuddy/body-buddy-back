package BodyBuddy.demo.domain.web.Service;


import BodyBuddy.demo.domain.avatar.DTO.AvatarDTO;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.avatarSkin.entity.AvatarSkin;
import BodyBuddy.demo.domain.avatarSkin.repository.AvatarSkinRepository;
import BodyBuddy.demo.domain.inBody.repository.InBodyRepository;
import BodyBuddy.demo.global.common.member.DTO.MemberDTO;
import BodyBuddy.demo.global.common.member.entity.Member;
import BodyBuddy.demo.global.common.member.repository.MemberRepository;
import BodyBuddy.demo.global.common.point.DTO.PointDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecoPageService {

  private final MemberRepository memberRepository;
  private final AvatarRepository avatarRepository;
  private final AvatarSkinRepository avatarSkinRepository;
  private final InBodyRepository inBodyRepository;

  /**
   포인트 총합 조회 로직
   */

  public PointDTO.Response getTotalPoints(Long memberId) {
    // 멤버 조회 (존재하지 않으면 예외 발생)
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

    return PointDTO.Response.builder()
        .amount(member.getTotalPoints())
        .build();
  }

  /**
   * 멤버 정보 조회 로직
   */

  public MemberDTO.Response getMemberInfo(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

    return MemberDTO.Response.builder()
        .id(member.getId())
        .nickName(member.getNickName())
        .build();
  }

  /**
   * 아바타 스킨 조회 로직
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
   * 카테고리별 아이템 조회 로직
   */



}
