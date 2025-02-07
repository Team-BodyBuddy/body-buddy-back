package BodyBuddy.demo.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI BodyBuddyAPI(ServletContext servletContext, Environment environment) {
		// API 기본 정보 설정
		Info info = new Info()
			.title("BodyBuddy API")
			.description("BodyBuddy 프로젝트의 API 명세서입니다.")
			.version("1.0.0");

		// JWT 인증 설정
		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER)
			.name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("bearerAuth");

		// API 서버 URL 설정
		List<Server> servers = new ArrayList<>();

		// 현재 실행 중인 환경이 배포인지 자동 감지
		String contextPath = servletContext.getContextPath();
		String baseUrl = contextPath.isEmpty() ? "/" : contextPath;
		boolean isLocal = environment.getActiveProfiles().length == 0 || "local".equals(environment.getActiveProfiles()[0]);

		// 로컬 환경인 경우
		if (isLocal) {
			servers.add(new Server().url("http://localhost:8080" + baseUrl).description("Local 환경"));
		}

		// 배포 환경인 경우 (서버 URL 자동 감지)
		servers.add(new Server().url("https://body-buddy.aoimiu.com").description("Production 환경"));

		// OpenAPI 설정 반환
		return new OpenAPI()
			.servers(servers)
			.info(info)
			.components(new Components().addSecuritySchemes("bearerAuth", securityScheme)) // JWT 인증 추가
			.addSecurityItem(securityRequirement); // 모든 API에 SecurityRequirement 추가
	}
}
