package com.seproject.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import java.util.List;

import com.seproject.backend.interceptors.AuthInterceptor;

/**
 * Web configuration class to handle proper page serialization and other web-related configurations
 */
@Configuration
@EnableSpringDataWebSupport
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                // for local development
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/auth/protected", "/api/teamspaces/**", "/api/projects/**",
                        "/api/tasks/**", "/api/deadlines/**", "/api/auth/send-verification-link",
                        "/api/auth/verify-email")
                .excludePathPatterns("/api/auth/login", "/api/auth/register");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        argumentResolvers.add(resolver);
    }
}