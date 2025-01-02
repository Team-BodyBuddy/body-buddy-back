package BodyBuddy.demo.ranking.repository;

import BodyBuddy.demo.ranking.entity.RankingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 랭킹 데이터 액세스 계층 (RankingRepository)
 * 랭킹 데이터를 데이터베이스에서 관리
 */
@Repository
public interface RankingRepository extends JpaRepository<RankingPoint, Long> {

    /**
     * 전체 랭킹을 총 점수 내림차순으로 정렬하여 반환
     */
    @Query("SELECT r FROM RankingPoint r ORDER BY r.totalScore DESC")
    List<RankingPoint> findTopRankings();

    /**
     * 특정 체육관의 회원 랭킹을 총 점수 내림차순으로 반환
     * @param gymId 체육관 ID
     */
    @Query("SELECT r FROM RankingPoint r WHERE r.member.gym.id = :gymId ORDER BY r.totalScore DESC")
    List<RankingPoint> findRankingsByGym(Long gymId);

    /**
     * 특정 회원의 랭킹 데이터 조회
     * @param memberId 회원 ID
     */
    @Query("SELECT r FROM RankingPoint r WHERE r.member.id = :memberId")
    RankingPoint findByMemberId(Long memberId);

    /**
     * 특정 회원의 순위를 반환 (네이티브 쿼리 사용)
     * @param memberId 회원 ID
     */
    @Query(value = "SELECT RANK() OVER (ORDER BY total_score DESC) FROM ranking_point WHERE member_id = :memberId", nativeQuery = true)
    int findRankByMemberId(Long memberId);

    /**
     * 모든 랭킹 데이터에 대해 순위를 부여하여 반환
     */
    @Query(value = "SELECT id, RANK() OVER (ORDER BY total_score DESC) AS rank FROM ranking_point", nativeQuery = true)
    List<Object[]> findAllWithRanks();
}
