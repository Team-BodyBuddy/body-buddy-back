package BodyBuddy.demo.domain.inbody.service;

import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import BodyBuddy.demo.domain.inbody.dto.AvatarInBodyDTO;
import BodyBuddy.demo.domain.inbody.dto.WeightHistoryListDTO;
import BodyBuddy.demo.domain.inbody.entity.InBody;
import BodyBuddy.demo.domain.inbody.repository.InBodyRepository;
import BodyBuddy.demo.global.common.commonEnum.InBodyStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InBodyService {

  private final InBodyRepository inBodyRepository;

  public AvatarInBodyDTO getAvatarInBody(Long memberId) {
    List<InBody> inBodies = inBodyRepository.findTop2ByMemberIdOrderByCreatedAtDesc(memberId);

    if (inBodies.isEmpty()) {
      throw new IllegalArgumentException("해당 회원의 인바디 데이터가 존재하지 않습니다.");
    }

    InBody current = inBodies.get(0); // 최신 인바디 데이터
    InBody previous = inBodies.size() > 1 ? inBodies.get(1) : null; // 직전 인바디 데이터 (없으면 null)

    return new AvatarInBodyDTO(
        memberId,
        current.getWeight(), current.getMuscle(), current.getBodyFat(),
        previous != null ? previous.getWeight() : current.getWeight(),  // 직전 데이터 없으면 현재 값 사용
        previous != null ? previous.getMuscle() : current.getMuscle(),
        previous != null ? previous.getBodyFat() : current.getBodyFat(),
        previous != null ? InBodyStatus.compare(current.getWeight(), previous.getWeight()) : InBodyStatus.SAME,
        previous != null ? InBodyStatus.compare(current.getMuscle(), previous.getMuscle()) : InBodyStatus.SAME,
        previous != null ? InBodyStatus.compare(current.getBodyFat(), previous.getBodyFat()) : InBodyStatus.SAME
    );
  }


  public WeightHistoryListDTO getRecentWeightHistory(Long memberId) {
    List<InBody> inBodies =inBodyRepository.findTop5ByMemberIdOrderByCreatedAtDesc(memberId);

    if (inBodies.isEmpty()) {
      throw new IllegalArgumentException("해당 회원의 인바디 데이터가 존재하지 않습니다.");
    }

    return WeightHistoryListDTO.from(inBodies);
  }

}
