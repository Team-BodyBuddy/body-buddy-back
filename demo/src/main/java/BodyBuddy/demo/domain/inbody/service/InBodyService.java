package BodyBuddy.demo.domain.inbody.service;

import BodyBuddy.demo.domain.inbody.converter.InBodyConverter;
import BodyBuddy.demo.domain.inbody.dto.InBodyResponseDTO;
import BodyBuddy.demo.domain.inbody.dto.WeightHistoryListDTO;
import BodyBuddy.demo.domain.inbody.entity.InBody;
import BodyBuddy.demo.domain.inbody.repository.InBodyRepository;
import BodyBuddy.demo.global.apiPayload.code.error.InbodyErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.common.commonEnum.InBodyStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InBodyService {

  private final InBodyRepository inBodyRepository;
  private final InBodyConverter inBodyConverter;

  /**
   * 최신 인바디 데이터 + 직전 데이터 비교
   */
  public InBodyResponseDTO.LatestData getLatestInBody(Long memberId) {
    List<InBody> inBodies = inBodyRepository.findTop2ByMemberIdOrderByCreatedAtDesc(memberId);

    InBody latest = inBodies.get(0);
    InBody previous = inBodies.size() > 1 ? inBodies.get(1) : null;

    return inBodyConverter.toLatestData(latest, previous);
  }

  /**
   * 최근 5개 인바디 데이터 조회
   */
  public List<InBodyResponseDTO.HistoryData> getRecentInBodyHistory(Long memberId) {
    List<InBody> inBodies = inBodyRepository.findTop5ByMemberIdOrderByCreatedAtDesc(memberId);

    return inBodyConverter.toHistoryDataList(inBodies);
  }
}

