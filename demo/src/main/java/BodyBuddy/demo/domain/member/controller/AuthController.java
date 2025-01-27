package BodyBuddy.demo.domain.member.controller;

import BodyBuddy.demo.domain.member.dto.SignUpRequestDto;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
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
    @PostMapping("/members/signup")
    public ApiResponse<Void> signupMember(@Valid @RequestBody SignUpRequestDto.MemberSignupRequest request) {
        memberService.signupMember(request);
        return ApiResponse.of(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    /**
     * 트레이너 회원가입
     */
    @PostMapping("/trainers/signup")
    public ApiResponse<Void> signupTrainer(@Valid @RequestBody SignUpRequestDto.TrainerSignupRequest request) {
        memberService.signupTrainer(request);
        return ApiResponse.of(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody SignUpRequestDto.MemberLoginRequest request) {
        String token = memberService.login(request);
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS, token);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteUser(@RequestParam String loginId) {
        memberService.deleteUser(loginId);
        return ApiResponse.of(SuccessStatus.DELETE_ACCOUNT_SUCCESS, null);
    }
}
