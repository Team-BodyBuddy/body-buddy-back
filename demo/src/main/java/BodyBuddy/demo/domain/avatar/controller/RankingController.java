package BodyBuddy.demo.domain.avatar.controller;

import BodyBuddy.demo.domain.avatar.dto.RankingResponse;
import BodyBuddy.demo.domain.avatar.dto.RankingResponse.GlobalRanking;
import BodyBuddy.demo.domain.avatar.dto.RankingResponse.UpdateResponse;
import BodyBuddy.demo.domain.avatar.service.RankingService;
import BodyBuddy.demo.domain.avatar.service.RankingUpdateService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor
@Tag(name = "Ranking API", description = "랭킹 관련 API")
public class RankingController {

    private final RankingService rankingService;
    private final RankingUpdateService rankingUpdateService;

    @GetMapping("/global")
    @Operation(summary = "글로벌 랭킹 조회", description = "전체 글로벌 랭킹을 조회합니다. 최대 55명까지 반환됩니다.")
    public ApiResponse<PagedModel<GlobalRanking>> getGlobalRankings(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "55") int size) {
        Page<GlobalRanking> rankings = rankingService.getGlobalRankings(page, size);
        PagedModel<GlobalRanking> pagedModel = PagedModel.of(
            rankings.getContent(),
            new PagedModel.PageMetadata(
                rankings.getSize(),
                rankings.getNumber(),
                rankings.getTotalElements(),
                rankings.getTotalPages()
            )
        );
        return ApiResponse.of(SuccessStatus.RANKINGSUCCESS, pagedModel);
    }

    @GetMapping("/gym/{gymId}")
    @Operation(summary = "GYM 랭킹 조회", description = "특정 GYM의 전체 랭킹을 조회합니다. 최대 55명까지 반환됩니다.")
    public ApiResponse<PagedModel<GlobalRanking>> getGymRankings(
        @PathVariable Long gymId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "55") int size) {
        Page<RankingResponse.GlobalRanking> rankings = rankingService.getGymRankings(gymId, page, size);
        PagedModel<RankingResponse.GlobalRanking> pagedModel = PagedModel.of(
            rankings.getContent(),
            new PagedModel.PageMetadata(
                rankings.getSize(),
                rankings.getNumber(),
                rankings.getTotalElements(),
                rankings.getTotalPages()
            )
        );
        return ApiResponse.of(SuccessStatus.RANKINGSUCCESS, pagedModel);
    }

    @GetMapping("/user/{memberId}")
    @Operation(summary = "회원 글로벌 순위 조회", description = "특정 회원의 글로벌 순위를 조회합니다.")
    public ApiResponse<RankingResponse.GlobalRanking> getUserGlobalRanking(@PathVariable Long memberId) {
        return ApiResponse.of(SuccessStatus.RANKINGSUCCESS, rankingService.getUserGlobalRanking(memberId));
    }

    @GetMapping("/user/{memberId}/gym/{gymId}")
    @Operation(summary = "회원 체육관 순위 조회", description = "특정 회원의 체육관 내 순위를 조회합니다.")
    public ApiResponse<RankingResponse.GlobalRanking> getUserGymRanking(@PathVariable Long memberId, @PathVariable Long gymId) {
        return ApiResponse.of(SuccessStatus.RANKINGSUCCESS, rankingService.getUserGymRanking(memberId, gymId));
    }

    @PostMapping("/update")
    @Operation(summary = "랭킹 업데이트", description = "모든 사용자의 랭킹 점수에 따라 포인트와 경험치를 업데이트합니다.")
    public ApiResponse<List<UpdateResponse>> updateRankings() {
        return ApiResponse.of(SuccessStatus.RANKINGUPDATE, rankingUpdateService.updateRankings());
    }
}