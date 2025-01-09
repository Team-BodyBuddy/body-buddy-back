package BodyBuddy.demo.global.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI BodyBuddyAPI() {
		// API 정보 설정
		Info info = new Info()
			.title("BodyBuddy API")
			.description("BodyBuddy 프로젝트의 API 명세서입니다.")
			.version("1.0.0");

		// OpenAPI 설정 반환
		return new OpenAPI()
			.addServersItem(new Server().url("/")) // 기본 서버 URL 설정
			.info(info)
			.components(new Components()); // SecuritySchemes 없이 설정
	}

}
