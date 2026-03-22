package com.example.eLeavePortal.security;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
                ) throws IOException {
                
        response.sendRedirect("/access-denied");
    }
}
