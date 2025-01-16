package BodyBuddy.demo.domain.ranking.controller;

import BodyBuddy.demo.domain.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.domain.ranking.service.RankingPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 랭킹 페이지네이션 컨트롤러
 * 글로벌 및 체육관 랭킹 데이터를 반환
 */
@RestController
@RequestMapping("/api/rankings/pagination")
@RequiredArgsConstructor
@Tag(name = "Ranking Pagination API", description = "랭킹 페이지 관련 API")
public class RankingPaginationController {

    private final RankingPaginationService rankingPaginationService;

    /**
     * 글로벌 랭킹 조회 (55명 + 사용자 랭킹 포함)
     * @param memberId 사용자 ID
     * @param page 페이지 번호
     * @param size 페이지 크기 (기본값 55)
     * @return 글로벌 랭킹 리스트
     */
    @Operation(
            summary = "글로벌 랭킹 조회",
            description = "글로벌 랭킹 데이터를 페이지네이션을 통해 조회합니다. 기본적으로 상위 55명의 데이터를 반환하며, 사용자 랭킹이 포함되지 않은 경우 별도로 추가됩니다."
    )
    @GetMapping("/global")
    public ResponseEntity<List<RankingResponseDto>> getGlobalRankingsWithUser(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "55") int size) {
        List<RankingResponseDto> rankings = rankingPaginationService.getGlobalRankingsWithUser(memberId, page, size);
        return ResponseEntity.ok(rankings);
    }

    /**
     * 특정 체육관 랭킹 조회 (55명 + 사용자 랭킹 포함)
     * @param gymId 체육관 ID
     * @param memberId 사용자 ID
     * @param page 페이지 번호
     * @param size 페이지 크기 (기본값 55)
     * @return 체육관 랭킹 리스트
     */
    @Operation(
            summary = "체육관 랭킹 조회",
            description = "특정 체육관의 랭킹 데이터를 페이지네이션을 통해 조회합니다. 기본적으로 상위 55명의 데이터를 반환하며, 사용자 랭킹이 포함되지 않은 경우 별도로 추가됩니다."
    )
    @GetMapping("/gym/{gymId}")
    public ResponseEntity<List<RankingResponseDto>> getGymRankingsWithUser(
            @PathVariable Long gymId,
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "55") int size) {
        List<RankingResponseDto> rankings = rankingPaginationService.getGymRankingsWithUser(gymId, memberId, page, size);
        return ResponseEntity.ok(rankings);
    }
}
