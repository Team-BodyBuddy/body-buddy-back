package BodyBuddy.demo.ranking.repository;

import BodyBuddy.demo.ranking.entity.RankingPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 랭킹 데이터 액세스 계층 (RankingRepositoryWithPagination)
 * 페이지네이션 및 사용자 랭킹 데이터를 관리
 */
@Repository
public interface RankingRepositoryWithPagination extends JpaRepository<RankingPoint, Long> {

    /**
     * 글로벌 랭킹 조회 (페이지네이션 지원)
     *
     * @param pageable 페이지 요청 정보
     * @return 페이지 형태의 글로벌 랭킹 데이터
     */
    @Query(value = """
                SELECT r
                FROM RankingPoint r
                ORDER BY r.totalScore DESC
            """)
    Page<RankingPoint> findPagedGlobalRankings(Pageable pageable);

    /**
     * 특정 체육관 랭킹 조회 (페이지네이션 지원)
     *
     * @param gymId    체육관 ID
     * @param pageable 페이지 요청 정보
     * @return 페이지 형태의 체육관 랭킹 데이터
     */
    @Query(value = """
                SELECT rp
                FROM RankingPoint rp
                JOIN rp.member m
                WHERE m.gym.id = :gymId
                ORDER BY rp.totalScore DESC
            """)
    Page<RankingPoint> findPagedRankingsByGymId(@Param("gymId") Long gymId, Pageable pageable);

    /**
     * 특정 사용자의 랭킹 데이터 조회
     *
     * @param memberId 사용자 ID
     * @return 사용자 랭킹 데이터
     */
    @Query("SELECT r FROM RankingPoint r WHERE r.member.id = :memberId")
    Optional<RankingPoint> findRankingByMemberId(@Param("memberId") Long memberId);

    /**
     * 특정 사용자의 글로벌 순위 조회
     *
     * @param memberId 사용자 ID
     * @return 사용자 글로벌 순위
     */
    @Query(value = """
                SELECT ranked.`rank`
                FROM (
                    SELECT member_id, RANK() OVER (ORDER BY total_score DESC) AS `rank`
                    FROM ranking_point
                ) ranked
                WHERE ranked.member_id = :memberId
            """, nativeQuery = true)
    Optional<Integer> findGlobalRankForMember(@Param("memberId") Long memberId);

    /**
     * 특정 회원의 체육관 내 랭킹 조회
     *
     * @param memberId 사용자 ID
     * @param gymId    체육관 ID
     * @return 사용자 체육관 내 순위
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
    Optional<Integer> findGymRankForMember(@Param("memberId") Long memberId, @Param("gymId") Long gymId);
}
