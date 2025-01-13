package BodyBuddy.demo.domain.ranking.controller;

import BodyBuddy.demo.domain.ranking.dto.RankingRequestDto;
import BodyBuddy.demo.domain.ranking.dto.RankingResponseDto;
import BodyBuddy.demo.domain.ranking.service.RankingService;
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
    @Operation(summary = "GYM 랭킹 조회", description = "특정 GYM의 전체 랭킹을 조회합니다.")
    public ResponseEntity<List<RankingResponseDto>> getGymRankings(@PathVariable Long gymId) {
        List<RankingResponseDto> rankings = rankingService.getGymRankings(gymId);
        return ResponseEntity.ok(rankings);
    }

    /**
     * 사용자 랭킹 조회 API
     * @param requestDto 요청 DTO (memberId와 gymId 포함)
     * @return 사용자 랭킹 정보
     */
    @PostMapping("/user")
    @Operation(summary = "사용자 랭킹 조회", description = "특정 체육관에서 사용자의 랭킹을 조회합니다.")
    public ResponseEntity<RankingResponseDto> getUserRanking(@RequestBody RankingRequestDto requestDto) {
        RankingResponseDto response = rankingService.getUserRanking(requestDto.getMemberId(), requestDto.getGymId());
        return ResponseEntity.ok(response);
    }

    /**
     * 랭킹 보너스 자동 지급 (월요일 자정)
     * 메시지 확인용 API
     */
    @GetMapping("/messages/{memberId}")
    @Operation(summary = "미확인 메시지 조회", description = "특정 사용자가 확인하지 않은 보너스 메시지를 조회합니다.")
    public ResponseEntity<List<String>> getUnreadMessages(@PathVariable Long memberId) {
        List<String> unreadMessages = rankingService.getUnreadNotifications(memberId);
        return ResponseEntity.ok(unreadMessages);
    }

    @PostMapping("/messages/clear/{memberId}")
    @Operation(summary = "미확인 메시지 초기화", description = "특정 사용자의 미확인 메시지를 초기화합니다.")
    public ResponseEntity<Void> clearUnreadMessages(@PathVariable Long memberId) {
        rankingService.clearNotifications(memberId);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 회원의 순위 조회 API
     * @param memberId 회원 ID
     * @return 회원의 순위
     */
    @GetMapping("/rank/{memberId}")
    @Operation(summary = "회원 순위 조회", description = "특정 회원의 전체 랭킹을 조회합니다.")
    public ResponseEntity<Integer> getMemberRank(@PathVariable Long memberId) {
        int rank = rankingService.getMemberRank(memberId);
        return ResponseEntity.ok(rank);
    }

    @GetMapping("/gym-name/{memberId}")
    @Operation(summary = "소속 GYM 이름 조회", description = "특정 회원의 소속 GYM 이름을 반환합니다.")
    public ResponseEntity<String> getGymName(@PathVariable Long memberId) {
        String gymName = rankingService.getGymNameByMemberId(memberId);
        if (gymName == null || gymName.isEmpty()) {
            return ResponseEntity.ok("소속되어 있는 GYM이 없습니다.");
        }
        return ResponseEntity.ok(gymName);
    }

    @GetMapping("/user-ranking/{memberId}")
    @Operation(summary = "사용자 랭킹 정보 조회", description = "특정 회원의 랭킹, 레벨, 닉네임, 점수를 반환합니다.")
    public ResponseEntity<RankingResponseDto> getUserRanking(@PathVariable Long memberId) {
        RankingResponseDto userRanking = rankingService.getUserRankingByMemberId(memberId);
        if (userRanking == null) {
            throw new RuntimeException("사용자 랭킹 정보 없음");
        }
        return ResponseEntity.ok(userRanking);
    }

    /**
     * TODO 테스트용 API: 모든 사용자에게 보상을 지급하고 메시지를 생성합니다.
     *
     * @return 보상 지급 결과 메시지
     */
    @PostMapping("/test-reward-all")
    @Operation(summary = "테스트용 전체 보상 지급", description = "모든 사용자에게 글로벌 및 GYM 랭킹 보상을 지급하고 메시지를 생성합니다.")
    public ResponseEntity<String> giveTestRewardToAll() {
        rankingService.updateRankingsAndRewards();
        return ResponseEntity.ok("모든 사용자에게 보상이 지급되었습니다.");
    }
}
