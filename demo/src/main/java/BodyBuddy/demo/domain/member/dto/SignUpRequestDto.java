package BodyBuddy.demo.domain.member.dto;

import BodyBuddy.demo.domain.gym.entity.Gym;
import BodyBuddy.demo.global.common.commonEnum.Gender;
import BodyBuddy.demo.global.common.commonEnum.Region;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import lombok.NoArgsConstructor;

public class SignUpRequestDto {

    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Getter
    @Builder
    public static class MemberSignupRequest {

        @NotBlank(message = "ID는 필수 입력 항목입니다.")
        private final String loginId;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$",
                message = "비밀번호는 8~12자, 영문, 숫자, 특수문자를 포함해야 합니다."
        )
        private final String password;

        @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
        private final String confirmPassword;

        @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
        private final String nickname;

        @NotBlank(message = "실명은 필수 입력 항목입니다.")
        private final String realName;

        @NotNull(message = "성별은 필수 입력 항목입니다.")
        private final Gender gender;

        @NotNull(message = "생년월일은 필수 입력 항목입니다.")
        private final LocalDate birthday;

        @NotNull(message = "키는 필수 입력 항목입니다.")
        private final Float height;

        @NotNull(message = "몸무게는 필수 입력 항목입니다.")
        private final Float weight;

        @NotNull(message = "지역은 필수 입력 항목입니다.")
        @Enumerated(EnumType.STRING)
        private Region region;

        @NotNull(message = "헬스장은 필수 입력 항목입니다.")
        private final Long gymId; // gym 대신 gymId 사용
    }

    @Getter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    public static class MemberLoginRequest {

        @NotBlank(message = "ID는 필수 입력 항목입니다.")
        private final String loginId;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        private final String password;
    }

    @Getter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    public static class TrainerSignupRequest {

        @NotBlank(message = "ID는 필수 입력 항목입니다.")
        private final String loginId;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$",
                message = "비밀번호는 8~12자, 영문, 숫자, 특수문자를 포함해야 합니다."
        )
        private final String password;

        @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
        private final String confirmPassword;

        @NotBlank(message = "실명은 필수 입력 항목입니다.")
        private final String realName;

        @NotNull(message = "성별은 필수 입력 항목입니다.")
        private final Gender gender;

        @NotNull(message = "생년월일은 필수 입력 항목입니다.")
        private final LocalDate birthday;

        @NotNull(message = "키는 필수 입력 항목입니다.")
        private final Float height;

        @NotNull(message = "몸무게는 필수 입력 항목입니다.")
        private final Float weight;

        @NotNull(message = "지역은 필수 입력 항목입니다.")
        @Enumerated(EnumType.STRING)
        private Region region;

        @NotNull(message = "헬스장은 필수 입력 항목입니다.")
        private final Long gymId; // gym 대신 gymId 사용
    }

    @Getter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    public static class TrainerLoginRequest {

        @NotBlank(message = "ID는 필수 입력 항목입니다.")
        private final String loginId;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        private final String password;
    }

    @Getter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    public static class JwtTokenRequest {
        @NotBlank(message = "RefreshToken은 필수 입력 항목입니다.")
        private final String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class JwtTokenResponse {
        private final String accessToken;
        private final String refreshToken;
    }
}
