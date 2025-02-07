package BodyBuddy.demo.domain.member.controller;

import BodyBuddy.demo.domain.member.dto.SignUpRequestDto;
import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.member.service.MemberService;
import BodyBuddy.demo.global.apiPayload.ApiResponse;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.code.status.SuccessStatus;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import BodyBuddy.demo.global.jwt.JwtTokenProvider;
import BodyBuddy.demo.global.service.JwtTokenService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    /**
     * 일반 회원 회원가입
     */
    @PostMapping("/members/signup")
    public ApiResponse<Void> signupMember(
        @Valid @RequestBody SignUpRequestDto.MemberSignupRequest request) {
        memberService.signupMember(request);
        return ApiResponse.of(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    /**
     * 트레이너 회원가입
     */
    @PostMapping("/trainers/signup")
    public ApiResponse<Void> signupTrainer(
        @Valid @RequestBody SignUpRequestDto.TrainerSignupRequest request) {
        memberService.signupTrainer(request);
        return ApiResponse.of(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(
        @Valid @RequestBody SignUpRequestDto.MemberLoginRequest request) {
        Map<String, String> tokens = memberService.login(request);
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS, tokens);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteUser(@RequestParam String loginId) {
        memberService.deleteUser(loginId);
        return ApiResponse.of(SuccessStatus.DELETE_ACCOUNT_SUCCESS, null);
    }

    /**
     * 리프레시 토큰을 사용해 새로운 토큰 재발급
     */
    @PostMapping("/refresh")
    public ApiResponse<SignUpRequestDto.JwtTokenResponse> refreshToken(
        @RequestBody @Valid SignUpRequestDto.JwtTokenRequest request) {
        SignUpRequestDto.JwtTokenResponse tokens = memberService.refreshTokens(request.getRefreshToken());
        return ApiResponse.of(SuccessStatus.TOKEN_REFRESH_SUCCESS, tokens);
    }
}
