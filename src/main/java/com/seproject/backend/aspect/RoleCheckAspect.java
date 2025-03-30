package com.seproject.backend.aspect;

import com.seproject.backend.util.SessionUtil;
import com.seproject.backend.annotations.RoleRequired;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class RoleCheckAspect {

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(roleRequired)")
    public void checkRole(RoleRequired roleRequired) {
        // role hierarchy, admin > project_manager > teamlead > user
        Map<String, Integer> roles = Map.of(
                "admin", 4,
                "project_manager", 3,
                "teamlead", 2,
                "user", 1);
        String requiredRole = roleRequired.value().toLowerCase();
        String userRole = SessionUtil.getRole(request).toLowerCase();
        if (!roles.containsKey(requiredRole)) {
            throw new IllegalArgumentException("Invalid role required");
        }
        if (userRole == null || !roles.containsKey(userRole) || roles.get(userRole) < roles.get(requiredRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Access denied: insufficient permissions; required role: "
                            + requiredRole + ", user role: " + userRole);
        }
    }
}