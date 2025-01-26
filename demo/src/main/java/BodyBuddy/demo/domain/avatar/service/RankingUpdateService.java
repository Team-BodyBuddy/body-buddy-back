package BodyBuddy.demo.domain.avatar.service;

import BodyBuddy.demo.domain.avatar.converter.RankingConverter;
import BodyBuddy.demo.domain.avatar.dto.RankingResponse;
import BodyBuddy.demo.domain.avatar.entity.Avatar;
import BodyBuddy.demo.domain.avatar.repository.AvatarRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RankingUpdateService {

    private final AvatarRepository avatarRepository;
    private final RankingConverter rankingConverter;

    @Transactional
    public List<RankingResponse.UpdateResponse> updateRankings() {
        List<Avatar> avatars = avatarRepository.findAllByOrderByRankingScoreDesc();
        List<RankingResponse.UpdateResponse> responses = new ArrayList<>();

        int rank = 1;
        for (Avatar avatar : avatars) {
            int points = calculatePoints(rank);
            int exp = calculateExp(rank);

            // 포인트와 경험치를 동시에 업데이트
            avatar.updatePointsAndExp(points, exp);

            avatarRepository.save(avatar);

            responses.add(rankingConverter.convertToUpdateResponse(avatar, points, exp));
            rank++;
        }

        return responses;
    }

    private int calculatePoints(int rank) {
        if (rank == 1) return 500;
        if (rank == 2) return 450;
        if (rank == 3) return 400;
        if (rank <= 5) return 300;
        if (rank <= 10) return 250;
        if (rank <= 20) return 200;
        if (rank <= 50) return 120;
        return 80;
    }

    private int calculateExp(int rank) {
        return calculatePoints(rank);
    }
}


