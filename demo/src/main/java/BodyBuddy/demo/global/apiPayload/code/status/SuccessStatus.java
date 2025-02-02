package BodyBuddy.demo.global.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import BodyBuddy.demo.global.apiPayload.code.BaseCode;
import BodyBuddy.demo.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

	// 일반적인 응답
	_OK(HttpStatus.OK, "COMMON200", "성공입니다."),

	// 로그인 관련 응답
	LOGIN_SUCCESS(HttpStatus.OK, "AUTH2000", "로그인 성공입니다."),

	// 회원가입 관련 응답
	SIGNUP_SUCCESS(HttpStatus.OK, "AUTH2010", "회원가입 성공입니다."),

	// 회원 탈퇴 관련 응답
	DELETE_ACCOUNT_SUCCESS(HttpStatus.OK, "AUTH2020", "회원 탈퇴 성공입니다."),

	// 랭킹 관련 응답
	RANKINGSUCCESS(HttpStatus.OK, "RANKING2000", "랭킹 조회 성공입니다."),
	RANKINGUPDATE(HttpStatus.OK, "RANKING2001", "랭킹 보너스 업데이트 성공입니다."),
	RANKINGSCOREUPDATE(HttpStatus.OK, "RANKING2002", "랭크 점수 업데이트 성공입니다."),

	//헬스장 관련 응답
	GYMSUCCESS(HttpStatus.OK, "GYM200", "체육관 조회 성공입니다."),

	//트레이너 관련 응답
	TRAINERS_BY_GYM_SUCCESS(HttpStatus.OK, "TRAINER200", "트레이너 조회 성공입니다."),
	TRAINER_INFO_SUCCESS(HttpStatus.OK, "TRAINER201", "트레이너 페이지 조회 성공입니다."),

	//지역 관련 응답
	REGIONSUCCESS(HttpStatus.OK, "REGION200", "지역 조회 성공입니다."),

	//뱃지 관련 응답
	BADGE_SUCCESS(HttpStatus.OK, "BADGE200", "뱃지 조회 성공입니다."),

	//포트폴리오 관련 응답
	PORTFOLIO_CREATED(HttpStatus.OK, "PORTFOLIO200", "포트폴리오 등록 성공입니다. "),
	PORTFOLIO_UPDATED(HttpStatus.OK, "PORTFOLIO201", "포트폴리오 업데이트 성공입니다. "),
	PORTFOLIO_FETCHED(HttpStatus.OK, "PORTFOLIO202", "포트폴리오 조회 성공입니다.")

	;


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReason() {
		return ReasonDTO.builder()
				.message(message)
				.code(code)
				.isSuccess(true)
				.build();
	}

	@Override
	public ReasonDTO getReasonHttpStatus() {
		return ReasonDTO.builder()
				.message(message)
				.code(code)
				.isSuccess(true)
				.httpStatus(httpStatus)
				.build()
				;
	}
}