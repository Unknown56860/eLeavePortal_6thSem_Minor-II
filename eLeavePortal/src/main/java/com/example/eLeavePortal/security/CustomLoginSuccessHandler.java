package com.example.eLeavePortal.security;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
                ) throws IOException {
        
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String redirectUrl;

        switch(role) {
            case "ADMIN": redirectUrl = "/"; break;

            case "STUDENT": redirectUrl = "/application/all"; break;

            case "CT": redirectUrl = "/under-construction"; break;
            case "HOD": redirectUrl = "/under-construction"; break;
            case "WARDEN": redirectUrl = "/under-construction"; break;
            case "HOSTEL_CONTROLLER": redirectUrl = "/under-construction"; break;

            default: redirectUrl = "/";
        }

        response.sendRedirect(redirectUrl);
    }
}
