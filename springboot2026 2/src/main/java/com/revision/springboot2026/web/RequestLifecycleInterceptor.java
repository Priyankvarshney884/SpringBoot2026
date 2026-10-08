package com.revision.springboot2026.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** Shows which controller method Spring MVC selected for an API request. */
// Unlike a servlet Filter, an MVC interceptor runs after Spring MVC has selected a handler.
@Component
public class RequestLifecycleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        // HandlerMapping selected a controller method; HandlerAdapter will invoke it next.
        if (handler instanceof HandlerMethod handlerMethod) {
            String selectedHandler = handlerMethod.getBeanType().getSimpleName()
                    + "#" + handlerMethod.getMethod().getName();
            // A demo header makes the selected handler visible with curl -i.
            response.setHeader("X-Mvc-Handler", selectedHandler);
        }
        return true; // true means continue to the controller; false would stop the request.
    }
}
