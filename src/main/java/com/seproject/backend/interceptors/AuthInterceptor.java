package com.seproject.backend.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.seproject.backend.util.JsonResponseUtil;
import com.seproject.backend.util.JwtUtil;
import com.seproject.backend.util.SessionUtil;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        jwtUtil.decodeJwtToken(token);
                        String username = jwtUtil.decodeJwtToken(token).getSubject();
                        String role = jwtUtil.decodeJwtToken(token).get("role", String.class);
                        SessionUtil.setUsername(request, username);
                        SessionUtil.setRole(request, role);
                        return true;
                    } catch (Exception e) {
                        JsonResponseUtil.jsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                                "Invalid access token");
                        return false;
                    }
                }
            }
        }
        response.setHeader("WWW-Authenticate", "Cookie realm=\"Access to the protected resource\"");
        JsonResponseUtil.jsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "No access token provided");

        return false;
    }
}