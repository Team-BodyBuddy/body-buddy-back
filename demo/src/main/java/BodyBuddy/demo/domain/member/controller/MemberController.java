package BodyBuddy.demo.domain.member.controller;

import BodyBuddy.demo.domain.member.dto.MemberResponseDto;
import BodyBuddy.demo.domain.member.dto.MemberUpdateRequestDto;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayLoad.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 데이터 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getMember(id)));
    }

    // 회원 데이터 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateMember(@PathVariable Long id,
        @RequestBody MemberUpdateRequestDto requestDto) {
        memberService.updateMember(id, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("Member updated successfully."));
    }
}
