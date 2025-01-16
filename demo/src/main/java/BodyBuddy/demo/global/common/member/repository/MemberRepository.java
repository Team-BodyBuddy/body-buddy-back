package BodyBuddy.demo.global.common.member.repository;

import BodyBuddy.demo.global.common.member.entity.Member;
import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Page<Member> findByTrainerId(Long trainerId, Pageable pageable);
}
