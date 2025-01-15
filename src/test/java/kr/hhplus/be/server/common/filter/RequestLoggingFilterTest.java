package kr.hhplus.be.server.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.BDDMockito.*;

class RequestLoggingFilterTest {

    @Test
    void shouldLogRequestDetails() throws IOException, ServletException {
        // Given
        RequestLoggingFilter filter = new RequestLoggingFilter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);


        given(request.getRequestURI()).willReturn("/api/test");
        given(request.getRemoteAddr()).willReturn("127.0.0.1");

        // When
        filter.doFilter(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
    }

}