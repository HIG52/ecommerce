package kr.hhplus.be.server.config.filter;

import kr.hhplus.be.server.common.filter.RequestWrappingInterceptorFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingFilterConfig {

    @Bean
    public FilterRegistrationBean<RequestWrappingInterceptorFilter> requestWrappingFilter() {
        FilterRegistrationBean<RequestWrappingInterceptorFilter> registrationBean
                = new FilterRegistrationBean<>(new RequestWrappingInterceptorFilter());
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
