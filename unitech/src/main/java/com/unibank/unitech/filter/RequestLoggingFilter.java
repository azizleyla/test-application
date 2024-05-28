package com.unibank.unitech.filter;

import com.unibank.unitech.entity.HttpLog;
import com.unibank.unitech.repository.HttpLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class RequestLoggingFilter extends HttpFilter {

    private final HttpLogRepository httpLogRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        log.info("RequestLoggingFilter initialized");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestDetails = logRequestDetails(request);

        try {

            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.error("Request processing failed: {}", e.getMessage(), e);
            logResponseDetails(response, request, requestDetails, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            throw e;
        }

        logResponseDetails(response, request, requestDetails, response.getStatus(),null );
    }

    private String logRequestDetails(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        log.info("Incoming Request:");
        log.info("Method: {}", request.getMethod());
        log.info("URI: {}", request.getRequestURI());
        log.info("Headers:\n{}", headers.toString());

        return headers.toString();
    }

    private void logResponseDetails(HttpServletResponse response, HttpServletRequest request, String requestDetails, int status, String errorMessage) {
        log.info("Response Status: {}", status);

        HttpLog httpLog = new HttpLog();
        httpLog.setMethod(request.getMethod());
        httpLog.setUrl(request.getRequestURI());
        httpLog.setHeaders(requestDetails);
        httpLog.setStatus(status);
        httpLog.setError(errorMessage);
        httpLog.setTimestamp(LocalDateTime.now());

        httpLogRepository.save(httpLog);
    }

    @Override
    public void destroy() {
        log.info("RequestLoggingFilter destroyed");
        super.destroy();
    }
}
