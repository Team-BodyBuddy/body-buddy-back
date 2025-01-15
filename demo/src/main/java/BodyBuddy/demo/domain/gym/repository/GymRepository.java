package BodyBuddy.demo.domain.gym.repository;

import BodyBuddy.demo.domain.gym.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository extends JpaRepository<Gym, Long> {
    /**
     * 특정 지역에 속한 헬스장 목록을 조회합니다.
     *
     * @param regionId 지역 ID
     * @return 헬스장 리스트
     */
    List<Gym> findByRegionId(Long regionId);
}
