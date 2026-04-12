package com.example.eLeavePortal.security;

import org.springframework.stereotype.Component;

import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
            HttpServletResponse response, Authentication authentication
                ) throws IOException {
        
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        RolesEnum role = user.getRole();
        ProfileStatusEnums profileStatus = user.getProfileStatus();
        
        if(profileStatus == ProfileStatusEnums.INCOMPLETE) {
            response.sendRedirect("/profile");
            return;
        }
        
        String redirectUrl;

        switch(role) {
            case ADMIN: redirectUrl = "/admin/site"; break;

            case STUDENT: redirectUrl = "/application/all"; break;

            case CT: redirectUrl = "/application/all"; break;
            case HOD: redirectUrl = "/application/all"; break;
            case WARDEN: redirectUrl = "/application/all"; break;
            case HOSTEL_CONTROLLER: redirectUrl = "/application/all"; break;

            default: redirectUrl = "/";
        }

        response.sendRedirect(redirectUrl);
    }
}
