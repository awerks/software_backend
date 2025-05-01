package com.seproject.backend.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class SessionUtil {

    public static String getUsername(HttpServletRequest request) {
        Object username = request.getSession().getAttribute("username");
        return username != null ? username.toString() : null;
    }

    public static String getRole(HttpServletRequest request) {
        Object role = request.getSession().getAttribute("role");
        return role != null ? role.toString() : null;
    }

    public static Integer getUserId(HttpServletRequest request) {
        Object userId = request.getSession().getAttribute("userId");
        return userId != null ? (Integer) userId : null;
    }

    public static void setUsername(HttpServletRequest request, String username) {
        request.getSession().setAttribute("username", username);
    }

    public static void setRole(HttpServletRequest request, String role) {
        request.getSession().setAttribute("role", role);
    }

    public static void setUserId(HttpServletRequest request, Integer userId) {
        request.getSession().setAttribute("userId", userId);
    }
}