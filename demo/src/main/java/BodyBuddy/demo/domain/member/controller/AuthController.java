package BodyBuddy.demo.domain.member.controller;

import BodyBuddy.demo.domain.member.dto.SignUpRequestDto;
import BodyBuddy.demo.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    /**
     * 일반 회원 회원가입
     */
    @PostMapping("/signup/member")
    public ResponseEntity<String> signupMember(@Valid @RequestBody SignUpRequestDto.MemberSignupRequest request) {
        memberService.signupMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("일반 회원 가입이 완료되었습니다.");
    }

    /**
     * 트레이너 회원가입
     */
    @PostMapping("/signup/trainer")
    public ResponseEntity<String> signupTrainer(@Valid @RequestBody SignUpRequestDto.TrainerSignupRequest request) {
        memberService.signupTrainer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("트레이너 회원 가입이 완료되었습니다.");
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody SignUpRequestDto.MemberLoginRequest request) {
        String token = memberService.login(request);
        return ResponseEntity.ok(token);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        memberService.logout();
        return ResponseEntity.ok("로그아웃 되었습니다. 클라이언트 토큰을 삭제해주세요.");
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String loginId) {
        memberService.deleteUser(loginId);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
