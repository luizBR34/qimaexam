package com.qima.exam.interceptor;

import com.qima.exam.error.NoBearerTokenError;
import com.qima.exam.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final AuthService service;

    public AuthorizationInterceptor(AuthService service) {
        this.service = service;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NoBearerTokenError();
        }
        request.setAttribute("user", service.getUserFromToken(authorizationHeader.substring(7)));
        return true;
    }
}
