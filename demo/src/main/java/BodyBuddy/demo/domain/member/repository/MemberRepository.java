package BodyBuddy.demo.domain.member.repository;


import BodyBuddy.demo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname); // 닉네임 중복 검사
    Optional<Member> findById(Long id);
    int countByTrainerId(Long trainerId);
    List<Member> findByTrainerId(Long trainerId);
}

