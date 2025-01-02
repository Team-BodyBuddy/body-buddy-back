package BodyBuddy.demo.ranking.controller;

import BodyBuddy.demo.ranking.dto.RankingRequestDto;
import BodyBuddy.demo.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.ranking.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 랭킹 컨트롤러 (RankingController)
 * 랭킹 관련 RESTful API를 제공
 */
@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
@Tag(name = "Ranking API", description = "랭킹 관련 API")
public class RankingController {

    private final RankingService rankingService;

    /**
     * 글로벌 랭킹 조회 API
     * @return 글로벌 랭킹 목록
     */
    @GetMapping("/global")
    @Operation(summary = "글로벌 랭킹 조회", description = "전체 글로벌 랭킹을 조회합니다.")
    public ResponseEntity<List<RankingResponseDto>> getGlobalRankings() {
        return ResponseEntity.ok(rankingService.getGlobalRankings());
    }

    /**
     * 특정 GYM 랭킹 조회 API
     * @param gymId 체육관 ID
     * @return 해당 체육관의 랭킹 목록
     */
    @GetMapping("/gym/{gymId}")
    @Operation(summary = "GYM 랭킹 조회", description = "특정 GYM의 랭킹을 조회합니다.")
    public ResponseEntity<List<RankingResponseDto>> getGymRankings(@PathVariable Long gymId) {
        return ResponseEntity.ok(rankingService.getGymRankings(gymId));
    }

    /**
     * 특정 사용자 랭킹 조회 API
     * @param requestDto 요청 DTO
     * @return 해당 사용자의 랭킹 정보
     */
    @PostMapping("/user")
    @Operation(summary = "사용자 랭킹 조회", description = "특정 사용자의 랭킹을 조회합니다.")
    public ResponseEntity<RankingResponseDto> getUserRanking(@RequestBody RankingRequestDto requestDto) {
        return ResponseEntity.ok(rankingService.getUserRanking(requestDto.getMemberId()));
    }

    /**
     * 랭킹 점수 및 보상 수동 갱신 API
     * @return 성공 메시지
     */
    @PostMapping("/update-rankings")
    @Operation(summary = "랭킹 점수 및 보상 갱신", description = "관리자가 수동으로 랭킹 점수와 보상을 갱신합니다.")
    public ResponseEntity<String> updateRankings() {
        rankingService.updateRankingsAndRewards();
        return ResponseEntity.ok("랭킹 점수 및 보상이 갱신되었습니다.");
    }

    /**
     * 특정 회원의 순위 조회 API
     * @param memberId 회원 ID
     * @return 회원의 순위
     */
    @GetMapping("/rank/{memberId}")
    @Operation(summary = "회원 순위 조회", description = "특정 회원의 순위를 조회합니다.")
    public ResponseEntity<Integer> getMemberRank(@PathVariable Long memberId) {
        int rank = rankingService.getMemberRank(memberId);
        return ResponseEntity.ok(rank);
    }
}
