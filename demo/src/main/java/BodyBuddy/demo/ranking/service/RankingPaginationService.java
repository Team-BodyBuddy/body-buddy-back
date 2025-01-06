package BodyBuddy.demo.ranking.service;

import BodyBuddy.demo.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.ranking.entity.RankingPoint;
import BodyBuddy.demo.ranking.repository.RankingRepository;
import BodyBuddy.demo.ranking.repository.RankingRepositoryWithPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 랭킹 페이지네이션 및 사용자 랭킹 포함 서비스
 */
@Service
@RequiredArgsConstructor
public class RankingPaginationService {

    private final RankingRepositoryWithPagination rankingRepositoryWithPagination;

    /**
     * 글로벌 랭킹 조회 (55명 + 사용자 랭킹 포함)
     * @param memberId 사용자 ID
     * @param page 페이지 번호
     * @param size 페이지 크기 (기본 55)
     * @return 글로벌 랭킹 리스트
     */
    public List<RankingResponseDto> getGlobalRankingsWithUser(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RankingPoint> rankings = rankingRepositoryWithPagination.findPagedGlobalRankings(pageable);

        List<RankingResponseDto> rankingList = rankings.stream()
                .map(ranking -> RankingResponseDto.fromRankingPoint(
                        rankings.getContent().indexOf(ranking) + 1,
                        ranking
                ))
                .collect(Collectors.toList());

        // 사용자 랭킹 조회
        RankingResponseDto userRanking = getUserRanking(memberId);

        // 사용자 랭킹이 상위 55명에 포함되지 않았다면 추가
        boolean isUserInRanking = rankingList.stream()
                .anyMatch(ranking -> ranking.getRank() == userRanking.getRank());

        if (!isUserInRanking) {
            rankingList.add(userRanking);
        }

        return rankingList;
    }

    /**
     * 체육관 랭킹 조회 (55명 + 사용자 랭킹 포함)
     * @param gymId 체육관 ID
     * @param memberId 사용자 ID
     * @param page 페이지 번호
     * @param size 페이지 크기 (기본 55)
     * @return 체육관 랭킹 리스트
     */
    public List<RankingResponseDto> getGymRankingsWithUser(Long gymId, Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RankingPoint> rankings = rankingRepositoryWithPagination.findPagedRankingsByGymId(gymId, pageable);

        // 추가 검증: 체육관 ID가 일치하지 않는 데이터를 필터링
        List<RankingResponseDto> rankingList = rankings.stream()
                .filter(ranking -> ranking.getMember().getGym().getId().equals(gymId)) // 체육관 ID 검증
                .map(ranking -> RankingResponseDto.fromRankingPoint(
                        rankings.getContent().indexOf(ranking) + 1,
                        ranking
                ))
                .collect(Collectors.toList());

        // 사용자 랭킹 추가
        RankingResponseDto userRanking = getUserRankingForGym(memberId, gymId);
        boolean isUserInRanking = rankingList.stream()
                .anyMatch(ranking -> ranking.getRank() == userRanking.getRank());

        if (!isUserInRanking) {
            rankingList.add(userRanking);
        }

        return rankingList;
    }

    /**
     * 사용자 랭킹 조회
     * @param memberId 사용자 ID
     * @return 사용자 랭킹 DTO
     */
    private RankingResponseDto getUserRanking(Long memberId) {
        RankingPoint ranking = rankingRepositoryWithPagination.findRankingByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int globalRank = rankingRepositoryWithPagination.findGlobalRankForMember(memberId)
                .orElseThrow(() -> new RuntimeException("Rank not found"));

        return RankingResponseDto.builder()
                .rank(globalRank)
                .nickname(ranking.getMember().getNickname())
                .level(ranking.getMember().getLevel())
                .totalScore(ranking.getTotalScore())
                .build();
    }

    private RankingResponseDto getUserRankingForGym(Long memberId, Long gymId) {
        // 체육관 내 사용자 랭킹 조회
        int gymRank = rankingRepositoryWithPagination.findGymRankForMember(memberId, gymId)
                .orElseThrow(() -> new RuntimeException("User not found in the specified gym"));

        // 사용자 랭킹 데이터 조회
        RankingPoint rankingPoint = rankingRepositoryWithPagination.findRankingByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("RankingPoint not found for user"));

        return RankingResponseDto.builder()
                .rank(gymRank)
                .nickname(rankingPoint.getMember().getNickname())
                .level(rankingPoint.getMember().getLevel())
                .totalScore(rankingPoint.getTotalScore())
                .build();
    }
}
