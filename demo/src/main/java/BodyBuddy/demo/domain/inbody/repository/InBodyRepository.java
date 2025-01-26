package BodyBuddy.demo.domain.inbody.repository;

import BodyBuddy.demo.domain.inbody.entity.InBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InBodyRepository extends JpaRepository<InBody, Long> {
    // 최신 두 개의 데이터를 가져오는 메서드
    List<InBody> findTop2ByMemberIdOrderByCreatedAtDesc(Long memberId);
}
