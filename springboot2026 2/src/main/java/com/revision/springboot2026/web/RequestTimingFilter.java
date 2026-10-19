package com.revision.springboot2026.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Measures how long an HTTP request takes through the servlet filter chain. */
// A servlet Filter runs before Spring MVC's DispatcherServlet and after its response returns.
// OncePerRequestFilter is Spring's helper for running this filter once per request dispatch.
@Component
public class RequestTimingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestTimingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        long startedAt = System.nanoTime();

        try {
            // Continue to the next filter; eventually the chain reaches DispatcherServlet.
            filterChain.doFilter(request, response);
        } finally {
            // finally also runs if downstream processing throws an exception.
            long elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000;
            logger.info("{} {} completed with status {} in {} ms",
                    request.getMethod(), request.getRequestURI(), response.getStatus(), elapsedMillis);
        }
    }
}
