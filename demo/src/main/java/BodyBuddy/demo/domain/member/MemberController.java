package BodyBuddy.demo.domain.member;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 회원 생성 API
     */
    @PostMapping
    @Operation(
        summary = "회원 생성",
        description = "새로운 회원을 생성합니다. 이름, 이메일, 비밀번호 등의 정보를 입력하세요."
    )
    public ResponseEntity<Member> createMember(@RequestBody @Valid MemberRequest request) {
        Member member = memberService.createMember(request);
        return ResponseEntity.ok(member);
    }

    /**
     * 모든 회원 조회 API
     */
    @GetMapping
    @Operation(
        summary = "모든 회원 조회",
        description = "등록된 모든 회원 정보를 반환합니다."
    )
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * 특정 회원 조회 API
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "특정 회원 조회",
        description = "회원 ID를 기반으로 특정 회원의 정보를 반환합니다."
    )
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * 회원 삭제 API
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "회원 삭제",
        description = "회원 ID를 기반으로 특정 회원을 삭제합니다."
    )
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
