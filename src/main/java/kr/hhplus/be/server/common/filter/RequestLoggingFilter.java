package kr.hhplus.be.server.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest httpRequest) {
            String ipAddress = getClientIp(httpRequest);
            String url = httpRequest.getRequestURI();

            log.info("Request IP: {}, URL: {}", ipAddress, url);
        }

        // 필터 체인을 통해 요청을 다음 단계로 전달
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
