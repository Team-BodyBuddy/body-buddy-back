package BodyBuddy.demo.domain.member.repository;


import BodyBuddy.demo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    Optional<Member> findById(Long id);

    int countByTrainerId(Long trainerId);

    List<Member> findByTrainerId(Long trainerId);

    @Modifying
    @Query("UPDATE Member m SET m.refreshToken = :refreshToken WHERE m.loginId = :loginId")
    void updateRefreshToken(@Param("loginId") String loginId, @Param("refreshToken") String refreshToken);
}

