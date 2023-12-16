package pl.edu.pb.wi.mmm.controller.handlers;// Filtr do logowania żądań
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pb.wi.mmm.entity.RequestLog;
import pl.edu.pb.wi.mmm.repository.RequestLogRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.security.Principal;


public class RequestLoggingFilter implements Filter {

    private final RequestLogRepository requestLogRepository;

    public RequestLoggingFilter(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String user = Optional.ofNullable(httpRequest.getUserPrincipal())
                .map(Principal::getName)
                .orElse("anonymous");
        String endpoint = httpRequest.getRequestURI();
        String requestData = httpRequest.getInputStream().toString();
        String responseData = httpResponse.getOutputStream().toString();
        String clientIp = httpRequest.getRemoteAddr();


        RequestLog requestLog = new RequestLog();
        requestLog.setUzer(user);
        requestLog.setEndpoint(endpoint);
        requestLog.setRequestData(requestData);
        requestLog.setResponseData(responseData);
        requestLog.setTimestamp(LocalDateTime.now());
        requestLog.setIp(clientIp);

        requestLogRepository.save(requestLog);

        chain.doFilter(request, response);
    }
}