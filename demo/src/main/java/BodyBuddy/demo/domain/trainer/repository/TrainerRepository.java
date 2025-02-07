package BodyBuddy.demo.domain.trainer.repository;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

    @Query("UPDATE Trainer t SET t.refreshToken = :refreshToken WHERE t.loginId = :loginId")
    @Modifying
    @Transactional
    default void updateRefreshToken(String loginId, String refreshToken) {
        Trainer trainer = findByLoginId(loginId)
            .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
        trainer.setRefreshToken(refreshToken);
    }
}
