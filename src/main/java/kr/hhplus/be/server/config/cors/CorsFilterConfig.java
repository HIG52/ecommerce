package kr.hhplus.be.server.config.cors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 자격 증명(쿠키 등)을 허용할지 설정
        config.addAllowedOriginPattern("*"); // 허용할 도메인, *로 모든 도메인 허용
        config.addAllowedHeader("*"); // 모든 요청 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드(GET, POST 등) 허용

        // 명시적으로 CORS 헤더 추가
        config.addExposedHeader("Access-Control-Allow-Headers");

        // UrlBasedCorsConfigurationSource에 CORS 설정 등록
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 설정 적용

        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
        filterRegistrationBean.setOrder(0);

        // CorsFilter 생성 및 반환
        return new CorsFilter(source);
    }
}
