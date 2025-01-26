package BodyBuddy.demo.domain.inbody.controller;

import BodyBuddy.demo.domain.inbody.dto.InBodyToRankingScoreDTO;
import BodyBuddy.demo.domain.inbody.service.InBodyToRankingScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ranking-score")
@RequiredArgsConstructor
public class InBodyToRankingScoreController {

    private final InBodyToRankingScoreService inBodyToRankingScoreService;

    @PostMapping("/{memberId}")
    public ResponseEntity<InBodyToRankingScoreDTO> updateRankingScore(@PathVariable Long memberId) {
        InBodyToRankingScoreDTO inBodyToRankingScoreDTO = inBodyToRankingScoreService.calculateAndSaveRankingScore(memberId);
        return ResponseEntity.ok(inBodyToRankingScoreDTO);
    }
}
