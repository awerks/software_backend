package com.seproject.backend.util;

import jakarta.servlet.http.HttpServletRequest;

public class SessionUtil {

    public static String getUsername(HttpServletRequest request) {
        Object username = request.getSession().getAttribute("username");
        return username != null ? username.toString() : null;
    }

    public static String getRole(HttpServletRequest request) {
        Object role = request.getSession().getAttribute("role");
        return role != null ? role.toString() : null;
    }

    public static void setUsername(HttpServletRequest request, String username) {
        request.getSession().setAttribute("username", username);
    }

    public static void setRole(HttpServletRequest request, String role) {
        request.getSession().setAttribute("role", role);
    }
}