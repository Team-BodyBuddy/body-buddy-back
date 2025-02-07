package BodyBuddy.demo.global.service;

import BodyBuddy.demo.domain.member.entity.Member;
import BodyBuddy.demo.domain.member.repository.MemberRepository;
import BodyBuddy.demo.domain.trainer.entity.Trainer;
import BodyBuddy.demo.domain.trainer.repository.TrainerRepository;
import BodyBuddy.demo.global.apiPayload.code.error.MemberErrorCode;
import BodyBuddy.demo.global.apiPayload.exception.BodyBuddyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    @Value("${auth.secret-key}")
    private String secretKey;

    private final long accessValidity = 3600000; // 1시간
    private final long refreshValidity = 604800000; // 7일

    @Transactional
    public Map<String, String> generateTokens(String loginId, String role) {
        String accessToken = createToken(loginId, role, accessValidity);
        String refreshToken = createToken(loginId, null, refreshValidity);

        if ("ROLE_MEMBER".equals(role)) {
            Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
            member.setRefreshToken(refreshToken);
            memberRepository.save(member);
        } else if ("ROLE_TRAINER".equals(role)) {
            Trainer trainer = trainerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
            trainer.setRefreshToken(refreshToken);
            trainerRepository.save(trainer);
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    @Transactional
    public Map<String, String> generateAndStoreTokens(String loginId, String role) {
        String accessToken = createToken(loginId, role, accessValidity);
        String refreshToken = createToken(loginId, null, refreshValidity);

        if ("ROLE_TRAINER".equals(role)) {
            Trainer trainer = trainerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
            trainer.setRefreshToken(refreshToken);
            trainerRepository.save(trainer);
        } else if ("ROLE_MEMBER".equals(role)) {
            Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BodyBuddyException(MemberErrorCode.NOT_FOUND_ID));
            member.setRefreshToken(refreshToken);
            memberRepository.save(member);
        }

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    private String createToken(String username, String role, long validity) {
        Claims claims = Jwts.claims().setSubject(username);
        if (role != null) {
            claims.put("role", role);
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validity);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
}
