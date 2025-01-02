package BodyBuddy.demo.global.common.controller;

import BodyBuddy.demo.global.common.dto.MemberDto;
import BodyBuddy.demo.global.common.entity.ActivityType;
import BodyBuddy.demo.global.common.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 회원 컨트롤러 (MemberController)
 * 회원 관련 API를 제공
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService; // 서비스 계층

    /**
     * 모든 회원 조회 API
     * @return 회원 목록
     */
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * 특정 회원 조회 API
     * @param id 회원 ID
     * @return 회원 상세 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * 회원 생성 API
     * @param memberDto 회원 생성 요청 데이터
     * @return 생성된 회원 정보
     */
    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.createMember(memberDto));
    }

    /**
     * 경험치 추가 API
     * @param id 회원 ID
     * @param activityType 활동 유형
     * @param streakDays 연속 운동 일수
     * @param rank 순위
     * @return HTTP 상태코드
     */
    @PostMapping("/{id}/experience")
    public ResponseEntity<Void> addExperience(
            @PathVariable Long id,
            @RequestParam ActivityType activityType,
            @RequestParam int streakDays,
            @RequestParam int rank
    ) {
        memberService.addExperience(id, activityType, streakDays, rank);
        return ResponseEntity.ok().build();
    }
}
