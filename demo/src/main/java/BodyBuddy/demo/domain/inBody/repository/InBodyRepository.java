package BodyBuddy.demo.domain.inBody.repository;

import BodyBuddy.demo.domain.inBody.domain.InBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InBodyRepository extends JpaRepository {
    Optional<InBody> findFirstByMemberIdOrderByCreatedAtDesc(Long memberId);
}
