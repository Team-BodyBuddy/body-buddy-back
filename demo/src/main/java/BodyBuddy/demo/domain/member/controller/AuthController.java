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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원가입, 로그인 및 계정 관리 API")
public class AuthController {

    private final MemberService memberService;

    /**
     * 일반 회원 회원가입
     */
    @Operation(summary = "일반 회원가입", description = "일반 사용자의 회원가입을 처리합니다.")
    @PostMapping("/members/signup")
    public ApiResponse<Void> signupMember(
        @Valid @RequestBody SignUpRequestDto.MemberSignupRequest request) {
        memberService.signupMember(request);
        return ApiResponse.of(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    /**
     * 트레이너 회원가입
     */
    @Operation(summary = "트레이너 회원가입", description = "트레이너 사용자의 회원가입을 처리합니다.")
    @PostMapping("/trainers/signup")
    public ApiResponse<Void> signupTrainer(
        @Valid @RequestBody SignUpRequestDto.TrainerSignupRequest request) {
        memberService.signupTrainer(request);
        return ApiResponse.of(SuccessStatus.SIGNUP_SUCCESS, null);
    }

    /**
     * 로그인
     */
    @Operation(summary = "로그인", description = "사용자의 로그인 요청을 처리하고 JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(
        @Valid @RequestBody SignUpRequestDto.MemberLoginRequest request) {
        Map<String, String> tokens = memberService.login(request);
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS, tokens);
    }

    /**
     * 회원 탈퇴
     */
    @Operation(summary = "회원 탈퇴", description = "회원이 자신의 계정을 삭제합니다.")
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteUser(@RequestParam String loginId) {
        memberService.deleteUser(loginId);
        return ApiResponse.of(SuccessStatus.DELETE_ACCOUNT_SUCCESS, null);
    }

    /**
     * 리프레시 토큰을 사용해 새로운 토큰 재발급
     */
    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 사용해 새로운 액세스 토큰을 발급합니다.")
    @PostMapping("/refresh")
    public ApiResponse<SignUpRequestDto.JwtTokenResponse> refreshToken(
        @RequestBody @Valid SignUpRequestDto.JwtTokenRequest request) {
        SignUpRequestDto.JwtTokenResponse tokens = memberService.refreshTokens(request.getRefreshToken());
        return ApiResponse.of(SuccessStatus.TOKEN_REFRESH_SUCCESS, tokens);
    }
}
