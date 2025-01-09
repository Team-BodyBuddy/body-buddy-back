package BodyBuddy.demo.domain.memberInbody.repository;

import BodyBuddy.demo.domain.memberInbody.MemberInbody;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInbodyRepository extends JpaRepository<MemberInbody, Long> {
    Optional<MemberInbody> findFirstByMemberIdOrderByUploadDateDesc(Long memberId);
}
