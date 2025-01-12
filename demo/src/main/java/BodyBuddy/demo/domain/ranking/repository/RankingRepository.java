package BodyBuddy.demo.domain.ranking.repository;

import BodyBuddy.demo.domain.ranking.entity.RankingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
     *
     * @param gymId 체육관 ID
     */
    @Query(value = """
                SELECT
                    ranked.`rank`,
                    ranked.member_id,
                    ranked.nickname,
                    ranked.total_score,
                    ranked.level
                FROM (
                    SELECT
                        rp.member_id,
                        m.nickname,
                        m.level,
                        RANK() OVER (PARTITION BY m.gym_id ORDER BY rp.total_score DESC) AS `rank`,
                        rp.total_score
                    FROM ranking_point rp
                    JOIN member m ON rp.member_id = m.id
                    WHERE m.gym_id = :gymId
                ) ranked
                ORDER BY ranked.`rank` ASC;
            """, nativeQuery = true)
    List<Object[]> findRankingsByGymId(@Param("gymId") Long gymId);

    /**
     * 특정 회원의 랭킹 데이터 조회
     *
     * @param memberId 회원 ID
     */
    @Query("SELECT r FROM RankingPoint r WHERE r.member.id = :memberId")
    RankingPoint findByMemberId(Long memberId);

    /**
     * 특정 회원의 순위를 반환 (네이티브 쿼리 사용)
     *
     * @param memberId 회원 ID
     */
    @Query(value = """
                SELECT ranked.`rank`
                FROM (
                    SELECT member_id, RANK() OVER (ORDER BY total_score DESC) AS `rank`
                    FROM ranking_point
                ) AS ranked
                WHERE ranked.member_id = :memberId
            """, nativeQuery = true)
    int findRankByMemberId(@Param("memberId") Long memberId);

    /**
     * 특정 회원의 체육관 내 랭킹 조회
     *
     * @param memberId 회원 ID
     * @param gymId    체육관 ID
     * @return 랭킹 데이터
     */
    @Query(value = """
                SELECT ranked.rank_value
                FROM (
                    SELECT member_id, RANK() OVER (ORDER BY total_score DESC) AS rank_value
                    FROM ranking_point rp
                    JOIN member m ON rp.member_id = m.id
                    WHERE m.gym_id = :gymId
                ) ranked
                WHERE ranked.member_id = :memberId
            """, nativeQuery = true)
    Optional<Integer> findRankByMemberIdAndGymId(@Param("memberId") Long memberId, @Param("gymId") Long gymId);
}


