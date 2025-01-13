package BodyBuddy.demo.domain.ranking.service;

import BodyBuddy.demo.domain.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.domain.ranking.entity.RankingPoint;
import BodyBuddy.demo.domain.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            throw new RuntimeException("사용자는 지정된 체육관에 소속되어 있지 않거나 랭크가 없습니다.");
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
     * 매주 월요일 자정에 랭킹 보너스 지급 및 메시지 저장
     */
    @Scheduled(cron = "0 0 0 * * MON")
    public void updateRankingsAndRewards() {
        List<RankingPoint> rankings = rankingRepository.findTopRankings();
        List<String> messages = new ArrayList<>();

        // 1. 바디버디 리그 보너스 지급
        messages.addAll(distributeBonusesForLeague(rankings, "바디버디 리그"));

        // 2. GYM 리그 보너스 지급
        List<String> gyms = rankingRepository.findAllGyms();
        for (String gym : gyms) {
            List<RankingPoint> gymRankings = rankingRepository.findRankingsByGymName(gym);
            messages.addAll(distributeBonusesForLeague(gymRankings, gym + " 리그"));
        }

        // 로그에 메시지 출력
        messages.forEach(log::info);
        log.info("랭킹 보너스 지급 및 메시지 저장 완료.");
    }

    /**
     * 특정 리그의 보너스를 지급하고 메시지를 생성하여 저장
     * @param rankings 순위별 데이터
     * @param leagueName 리그 이름
     * @return 생성된 메시지 리스트
     */
    private List<String> distributeBonusesForLeague(List<RankingPoint> rankings, String leagueName) {
        List<String> messages = new ArrayList<>();

        for (int i = 0; i < rankings.size(); i++) {
            RankingPoint ranking = rankings.get(i);
            int rank = i + 1;

            // 순위별 보너스 계산
            int pointBonus = getPointBonusByRank(rank);
            int expBonus = getExpBonusByRank(rank);

            // 보너스 지급
            ranking.setTotalScore(ranking.getTotalScore() + pointBonus);
            ranking.getMember().addExperience(expBonus);

            // 메시지 생성 및 저장
            String message = String.format(
                    "%s %d위 달성! +%d 포인트와 +%d 경험치를 획득했습니다.",
                    leagueName, rank, pointBonus, expBonus
            );
            ranking.addNotification(message);
            messages.add(message);
        }

        // 변경된 랭킹 저장
        rankingRepository.saveAll(rankings);
        return messages;
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

    /**
     * 특정 사용자의 미확인 알림 메시지를 조회합니다.
     *
     * @param memberId 사용자 ID
     * @return 사용자의 미확인 알림 메시지 리스트
     */
    public List<String> getUnreadNotifications(Long memberId) {
        RankingPoint rankingPoint = rankingRepository.findRankingByMemberId(memberId);
        return rankingPoint.getUnreadNotifications();
    }

    /**
     * 특정 사용자의 미확인 알림 메시지를 초기화(삭제)합니다.
     *
     * @param memberId 사용자 ID
     */
    public void clearNotifications(Long memberId) {
        RankingPoint rankingPoint = rankingRepository.findRankingByMemberId(memberId);
        rankingPoint.clearNotifications();
        rankingRepository.save(rankingPoint);
    }

    /**
     * 회원 ID를 기반으로 소속 GYM 이름 조회
     * @param memberId 회원 ID
     * @return 소속 GYM 이름
     */
    public String getGymNameByMemberId(Long memberId) {
        // 회원 ID를 통해 소속된 GYM 이름 조회
        String gymName = rankingRepository.findGymNameByMemberId(memberId);
        if (gymName == null || gymName.isEmpty()) {
            return "소속되어 있는 GYM이 없습니다.";
        }
        return gymName;
    }

    /**
     * 회원 ID를 기반으로 랭킹, 레벨, 닉네임, 점수 조회
     * @param memberId 회원 ID
     * @return 사용자 랭킹 정보
     */
    public RankingResponseDto getUserRankingByMemberId(Long memberId) {
        // 회원의 랭킹 정보 조회
        RankingPoint rankingPoint = rankingRepository.findRankingByMemberId(memberId);
        if (rankingPoint == null) {
            throw new RuntimeException("사용자 랭킹 정보 없음");
        }

        return RankingResponseDto.builder()
                .rank(rankingRepository.findRankByMemberId(memberId)) // 사용자 순위 조회
                .nickname(rankingPoint.getMember().getNickname())
                .level(rankingPoint.getMember().getLevel())
                .totalScore(rankingPoint.getTotalScore())
                .build();
    }
}