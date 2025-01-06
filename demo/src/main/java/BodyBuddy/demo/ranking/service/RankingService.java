package BodyBuddy.demo.ranking.service;

import BodyBuddy.demo.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.ranking.entity.RankingPoint;
import BodyBuddy.demo.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 랭킹 서비스 계층
 * 랭킹 데이터를 관리하고 점수 계산 및 보상을 처리합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RankingService {

    private final RankingRepository rankingRepository;

    private static final int[] POINT_BONUSES = {500, 450, 400, 300, 250, 200, 120, 80};
    private static final int[] EXP_BONUSES = {500, 450, 400, 300, 250, 200, 120, 80};

    /**
     * 글로벌 랭킹 조회
     * @return 글로벌 랭킹 목록
     */
    public List<RankingResponseDto> getGlobalRankings() {
        List<RankingPoint> rankings = rankingRepository.findTopRankings();
        return rankings.stream()
                .map(ranking -> RankingResponseDto.fromRankingPoint(rankings.indexOf(ranking) + 1, ranking))
                .collect(Collectors.toList());
    }

    /**
     * 특정 체육관 랭킹 조회
     * @param gymId 체육관 ID
     * @return 체육관 랭킹 목록
     */
    public List<RankingResponseDto> getGymRankings(Long gymId) {
        List<Object[]> rankings = rankingRepository.findRankingsByGymId(gymId);

        return rankings.stream()
                .map(row -> {
                    int rank = (row[0] != null) ? ((Number) row[0]).intValue() : 0;
                    Long memberId = (row[1] != null) ? ((Number) row[1]).longValue() : null;
                    String nickname = (row[2] != null) ? (String) row[2] : "Unknown";
                    int totalScore = (row[3] != null) ? ((Number) row[3]).intValue() : 0;
                    int level = (row[4] != null) ? ((Number) row[4]).intValue() : 0;

                    return RankingResponseDto.builder()
                            .rank(rank)
                            .nickname(nickname)
                            .level(level)
                            .totalScore(totalScore)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 특정 회원의 체육관 내 랭킹 조회
     * @param memberId 회원 ID
     * @param gymId 체육관 ID
     * @return 사용자 랭킹
     */
    public RankingResponseDto getUserRanking(Long memberId, Long gymId) {
        Optional<Integer> rankOptional = rankingRepository.findRankByMemberIdAndGymId(memberId, gymId);
        if (rankOptional.isEmpty()) {
            throw new RuntimeException("The user does not belong to the specified gym or has no rank.");
        }

        RankingPoint ranking = rankingRepository.findByMemberId(memberId);
        return RankingResponseDto.builder()
                .rank(rankOptional.get())
                .nickname(ranking.getMember().getNickname())
                .level(ranking.getMember().getLevel())
                .totalScore(ranking.getTotalScore())
                .build();
    }

    /**
     * 특정 회원 순위 조회
     * @param memberId 회원 ID
     * @return 회원의 순위
     */
    public int getMemberRank(Long memberId) {
        return rankingRepository.findRankByMemberId(memberId);
    }

    /**
     * 랭킹 점수 및 경험치 갱신
     */
    @Scheduled(cron = "0 0 0 * * MON")
    public void updateRankingsAndRewards() {
        List<RankingPoint> rankings = rankingRepository.findAll();

        // 모든 랭킹 데이터에 대해 순위별 보너스 적용
        for (int i = 0; i < rankings.size(); i++) {
            RankingPoint ranking = rankings.get(i);
            int rank = i + 1;

            int pointBonus = getPointBonusByRank(rank);
            int expBonus = getExpBonusByRank(rank);

            ranking.setTotalScore(ranking.getTotalScore() + pointBonus);
            ranking.getMember().addExperience(expBonus);
        }

        rankingRepository.saveAll(rankings);
        log.info("Rankings and rewards updated successfully.");
    }

    /**
     * 순위별 포인트 보너스 계산
     * @param rank 순위
     * @return 보너스 포인트
     */
    private int getPointBonusByRank(int rank) {
        if (rank <= 0 || rank > POINT_BONUSES.length) {
            return POINT_BONUSES[POINT_BONUSES.length - 1];
        }
        return POINT_BONUSES[rank - 1];
    }

    /**
     * 순위별 경험치 보너스 계산
     * @param rank 순위
     * @return 보너스 경험치
     */
    private int getExpBonusByRank(int rank) {
        if (rank <= 0 || rank > EXP_BONUSES.length) {
            return EXP_BONUSES[EXP_BONUSES.length - 1];
        }
        return EXP_BONUSES[rank - 1];
    }
}