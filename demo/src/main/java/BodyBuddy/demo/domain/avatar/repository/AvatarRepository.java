package BodyBuddy.demo.domain.avatar.repository;

import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.gym.entity.Gym;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Page<Avatar> findAllByOrderByRankingScoreDesc(Pageable pageable);

    Page<Avatar> findByMemberGymOrderByRankingScoreDesc(Gym gym, Pageable pageable);

    Optional<Avatar> findByMemberId(Long memberId);

    List<Avatar> findAllByOrderByRankingScoreDesc();

    @Query("""
    SELECT COUNT(a) + 1
    FROM Avatar a
    WHERE a.rankingScore > (
        SELECT rankingScore
        FROM Avatar
        WHERE member.id = :memberId
    )
    """)
    int findGlobalRankForMember(@Param("memberId") Long memberId);


    @Query("""
    SELECT COUNT(a) + 1
    FROM Avatar a
    WHERE a.member.gym.id = :gymId
      AND a.rankingScore > (
          SELECT rankingScore
          FROM Avatar
          WHERE member.id = :memberId
      )
    """)
    int findGymRankForMember(@Param("memberId") Long memberId, @Param("gymId") Long gymId);

}
