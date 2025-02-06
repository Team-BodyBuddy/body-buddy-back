package BodyBuddy.demo.domain.trainer.repository;

import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.global.apiPayload.code.error.TrainerErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);

    default void updateRefreshToken(String loginId, String refreshToken) {
        Trainer trainer = findByLoginId(loginId)
            .orElseThrow(() -> new BodyBuddyException(TrainerErrorCode.TRAINER_NOT_FOUND));
        trainer.setRefreshToken(refreshToken);
        save(trainer);
    }
}
