package BodyBuddy.demo.domain.inbody.controller;

import BodyBuddy.demo.domain.inbody.dto.InBodyToRankingScoreDTO;
import BodyBuddy.demo.domain.inbody.service.InBodyToRankingScoreService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inbody")
@RequiredArgsConstructor
public class InBodyToRankingScoreController {

    private final InBodyToRankingScoreService inBodyToRankingScoreService;

    @PostMapping("/ranking-score/{memberId}")
    public ApiResponse<InBodyToRankingScoreDTO> updateRankingScore(@PathVariable Long memberId) {
        InBodyToRankingScoreDTO inBodyToRankingScoreDTO = inBodyToRankingScoreService.calculateAndSaveRankingScore(memberId);
        return ApiResponse.of(SuccessStatus.RANKINGSCOREUPDATE, inBodyToRankingScoreDTO);
    }
}
