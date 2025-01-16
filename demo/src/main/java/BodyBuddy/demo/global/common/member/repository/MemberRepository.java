package BodyBuddy.demo.global.common.member.repository;

import BodyBuddy.demo.global.common.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Page<Member> findByTrainerId(@Param("trainerId") Long trainerId, Pageable pageable);
}
