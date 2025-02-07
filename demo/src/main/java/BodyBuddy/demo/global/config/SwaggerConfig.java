package BodyBuddy.demo.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI BodyBuddyAPI(Environment environment) {
		// API 정보 설정
		Info info = new Info()
			.title("BodyBuddy API")
			.description("BodyBuddy 프로젝트의 API 명세서입니다.")
			.version("1.0.0");

		// JWT Security Scheme 설정
		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER)
			.name("Authorization");

		// Security Requirement 설정
		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("bearerAuth");

		// 서버 URL 설정
		Server localServer = new Server().url("http://localhost:8080"); // 로컬 개발 환경
		Server prodServer = new Server().url("https://body-buddy.aoimiu.com/api"); // 배포 환경

		// 환경 변수에 따라 서버 URL 설정
		List<Server> servers = environment.getActiveProfiles().length > 0 &&
			"prod".equals(environment.getActiveProfiles()[0]) ? List.of(prodServer) : List.of(localServer);

		// OpenAPI 설정 반환
		return new OpenAPI()
			.servers(servers)
			.info(info)
			.components(new Components().addSecuritySchemes("bearerAuth", securityScheme)) // SecuritySchemes 추가
			.addSecurityItem(securityRequirement); // 모든 API에 SecurityRequirement 추가
	}
}
