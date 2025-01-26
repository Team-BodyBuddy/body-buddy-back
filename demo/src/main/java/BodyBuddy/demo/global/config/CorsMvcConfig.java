package BodyBuddy.demo.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용된 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용된 HTTP 메서드
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .exposedHeaders("Authorization", "Content-Type") // 클라이언트에 노출할 헤더
                .allowCredentials(true); // 쿠키 또는 인증 헤더 허용
    }
}
