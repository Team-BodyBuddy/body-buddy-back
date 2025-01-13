package BodyBuddy.demo.domain.inBody.repository;

import BodyBuddy.demo.domain.inBody.entity.InBody;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InBodyRepository extends JpaRepository<InBody, Long> {
    List<InBody> findTop2ByMemberIdOrderByCreatedAtDesc(Long memberId);
    List<InBody> findTop5ByMemberIdOrderByCreatedAtDesc(Long memberId);
}
