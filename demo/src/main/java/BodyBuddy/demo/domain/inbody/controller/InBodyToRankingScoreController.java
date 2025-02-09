package BodyBuddy.demo.domain.inbody.controller;

import BodyBuddy.demo.domain.inbody.dto.InBodyToRankingScoreDTO;
import BodyBuddy.demo.domain.inbody.service.InBodyToRankingScoreService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inbody")
@RequiredArgsConstructor
@Tag(name = "인바디 랭킹계산", description = "회원의 인바디 데이터를 바탕으로 랭킹 점수를 업데이트하는 API")
public class InBodyToRankingScoreController {

    private final InBodyToRankingScoreService inBodyToRankingScoreService;
    @Operation(
        summary = "회원 인바디 랭킹 점수 업데이트",
        description = "회원의 최신 인바디 데이터를 기반으로 랭킹 점수를 계산하고 저장합니다."
    )
    @PostMapping("/ranking-score/{memberId}")
    public ApiResponse<InBodyToRankingScoreDTO> updateRankingScore(@PathVariable Long memberId) {
        InBodyToRankingScoreDTO inBodyToRankingScoreDTO = inBodyToRankingScoreService.calculateAndSaveRankingScore(memberId);
        return ApiResponse.of(SuccessStatus.RANKINGSCOREUPDATE, inBodyToRankingScoreDTO);
    }
}
