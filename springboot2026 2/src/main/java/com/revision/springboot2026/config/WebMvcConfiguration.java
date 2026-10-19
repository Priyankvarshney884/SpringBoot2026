package com.revision.springboot2026.config;

import com.revision.springboot2026.web.RequestLifecycleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Registers the small Spring MVC interceptor used by the learning POC. */
// WebMvcConfigurer customizes MVC while keeping Spring Boot's MVC auto-configuration enabled.
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final RequestLifecycleInterceptor requestLifecycleInterceptor;

    public WebMvcConfiguration(RequestLifecycleInterceptor requestLifecycleInterceptor) {
        this.requestLifecycleInterceptor = requestLifecycleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Limit this teaching interceptor to API routes instead of every MVC resource.
        registry.addInterceptor(requestLifecycleInterceptor).addPathPatterns("/api/**");
    }
}
