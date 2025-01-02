package BodyBuddy.demo.global.common.repository;

import BodyBuddy.demo.global.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 특정 체육관에 소속된 회원 목록 조회
     * @param gymId 체육관 ID
     * @return 회원 목록
     */
    List<Member> findByGymId(Long gymId);
}

