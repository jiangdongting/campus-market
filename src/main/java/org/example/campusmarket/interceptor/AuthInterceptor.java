package org.example.campusmarket.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.campusmarket.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        }

        try {
            String token = authorization.substring(7);

            Claims claims = jwtUtil.parseToken(token);

            Long userId = ((Number) claims.get("userId")).longValue();
            String username = claims.getSubject();

            request.setAttribute("userId", userId);
            request.setAttribute("username", username);

            return true;

        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}