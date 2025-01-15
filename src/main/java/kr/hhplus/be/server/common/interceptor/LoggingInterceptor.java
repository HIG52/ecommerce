package kr.hhplus.be.server.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final Exception ex
    ) throws Exception {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;

        log.info(
                "\n HTTP Method : {} " +
                        "\n Request URI : {} " +
                        "\n AccessToken Exist : {} " +
                        "\n Request Body : {}",
                request.getMethod(),
                request.getRequestURI(),
                StringUtils.hasText(request.getHeader(HttpHeaders.AUTHORIZATION)),
                objectMapper.readTree(cachingRequest.getContentAsByteArray())
        );
    }

}
