package BodyBuddy.demo.ranking.service;

import BodyBuddy.demo.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.ranking.entity.RankingPoint;
import BodyBuddy.demo.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 랭킹 서비스 계층
 * 랭킹 데이터를 관리하고 점수 계산 및 보상을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;

    /**
     * 글로벌 랭킹 조회
     * @return 글로벌 랭킹 목록
     */
    public List<RankingResponseDto> getGlobalRankings() {
        List<RankingPoint> rankings = rankingRepository.findTopRankings();
        return rankings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 체육관 랭킹 조회
     * @param gymId 체육관 ID
     * @return 체육관 랭킹 목록
     */
    public List<RankingResponseDto> getGymRankings(Long gymId) {
        List<RankingPoint> rankings = rankingRepository.findRankingsByGym(gymId);
        return rankings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자 랭킹 조회
     * @param memberId 회원 ID
     * @return 사용자 랭킹 정보
     */
    public RankingResponseDto getUserRanking(Long memberId) {
        RankingPoint ranking = rankingRepository.findByMemberId(memberId);
        if (ranking == null) {
            throw new RuntimeException("Member ranking not found");
        }
        return toResponseDto(ranking);
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
     * RankingPoint를 RankingResponseDto로 변환
     * @param rankingPoint 랭킹 포인트 데이터
     * @return 변환된 응답 DTO
     */
    private RankingResponseDto toResponseDto(RankingPoint rankingPoint) {
        return RankingResponseDto.builder()
                .rank(rankingRepository.findRankByMemberId(rankingPoint.getMember().getId()))
                .nickname(rankingPoint.getMember().getNickname())
                .level(rankingPoint.getMember().getLevel())
                .totalScore(rankingPoint.getTotalScore())
                .build();
    }

    /**
     * 매주 월요일 0시에 랭킹 점수 갱신 및 보상 지급
     */
    @Scheduled(cron = "0 0 0 * * MON")
    public void updateRankingsAndRewards() {
        List<RankingPoint> allRankings = rankingRepository.findAll();

        // 데이터가 없는 경우 처리
        if (allRankings.isEmpty()) {
            System.out.println("랭킹 데이터가 없습니다.");
            return;
        }

        // 랭킹 점수 갱신
        allRankings.forEach(ranking -> {
            int newScore = calculateRankingScore(ranking);
            ranking.setTotalScore(newScore);
        });

        rankingRepository.saveAll(allRankings);

        // 보상 지급
        distributeRewards(allRankings);
        System.out.println("랭킹 갱신 및 보상이 지급되었습니다.");
    }

    /**
     * 랭킹 점수 계산
     * @param ranking 랭킹 포인트 객체
     * @return 계산된 점수
     */
    private int calculateRankingScore(RankingPoint ranking) {
        if (ranking == null || ranking.getMember() == null) {
            System.out.println("랭킹 데이터가 유효하지 않습니다.");
            return 0;
        }

        int baseScore = ranking.getTotalScore();
        int activityScore = calculateActivityScore(ranking);
        int goalBonus = calculateGoalBonus(ranking);

        double intensityMultiplier = getIntensityMultiplier(ranking);
        return (int) ((baseScore + activityScore + goalBonus) * intensityMultiplier);
    }

    /**
     * 운동량 기반 점수 계산
     * @param ranking 랭킹 포인트 객체
     * @return 운동량 점수
     */
    private int calculateActivityScore(RankingPoint ranking) {
        int activityTimeScore = 100; // 예제 값
        int calorieScore = 20;      // 예제 값
        return activityTimeScore + calorieScore;
    }

    /**
     * 목표 달성 보너스 점수 계산
     * @param ranking 랭킹 포인트 객체
     * @return 목표 달성 보너스 점수
     */
    private int calculateGoalBonus(RankingPoint ranking) {
        int weeklyGoalBonus = 500;  // 예제 값
        int monthlyGoalBonus = 1000; // 예제 값
        return weeklyGoalBonus + monthlyGoalBonus;
    }

    /**
     * 운동 강도 가중치 계산
     * @param ranking 랭킹 포인트 객체
     * @return 강도 가중치
     */
    private double getIntensityMultiplier(RankingPoint ranking) {
        return 1.2; // 예제 값
    }

    /**
     * 랭킹 순위에 따른 보상 지급
     * @param allRankings 전체 랭킹 리스트
     */
    private void distributeRewards(List<RankingPoint> allRankings) {
        if (allRankings == null || allRankings.isEmpty()) {
            System.out.println("보상 지급을 위한 데이터가 없습니다.");
            return;
        }

        allRankings.sort((a, b) -> b.getTotalScore() - a.getTotalScore());

        for (int i = 0; i < allRankings.size(); i++) {
            RankingPoint ranking = allRankings.get(i);
            if (ranking.getMember() == null) {
                System.out.println("회원 정보가 없는 랭킹 데이터가 있습니다.");
                continue;
            }

            int rank = i + 1;
            int rewardPoints = getRewardPointsByRank(rank);
            int rewardExperience = getRewardExperienceByRank(rank);

            ranking.getMember().setExperience(
                    ranking.getMember().getExperience() + rewardExperience
            );
            System.out.printf("Rank %d: Member %s awarded %d points and %d experience.\n",
                    rank, ranking.getMember().getNickname(), rewardPoints, rewardExperience);
        }
    }

    /**
     * 순위별 포인트 보너스 계산
     * @param rank 순위
     * @return 보너스 포인트
     */
    private int getRewardPointsByRank(int rank) {
        if (rank == 1) return 1000;
        if (rank <= 5) return 750;
        if (rank <= 10) return 500;
        if (rank <= 50) return 250;
        return 100;
    }

    /**
     * 순위별 경험치 보너스 계산
     * @param rank 순위
     * @return 보너스 경험치
     */
    private int getRewardExperienceByRank(int rank) {
        if (rank == 1) return 1000;
        if (rank <= 5) return 750;
        if (rank <= 10) return 500;
        if (rank <= 50) return 250;
        return 100;
    }
}
