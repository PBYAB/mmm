package pl.edu.pb.wi.mmm.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pb.wi.mmm.controller.handlers.RequestLoggingFilter;
import pl.edu.pb.wi.mmm.repository.RequestLogRepository;

@Configuration
public class FilterConfig {

    private final RequestLogRepository requestLogRepository;

    public FilterConfig(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestLoggingFilter(requestLogRepository));
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}