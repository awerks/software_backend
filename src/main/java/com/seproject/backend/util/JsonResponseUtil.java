package com.seproject.backend.util;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonResponseUtil {

    public static void jsonResponse(HttpServletResponse response, int status, String errorMessage)
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", errorMessage));
    }
}