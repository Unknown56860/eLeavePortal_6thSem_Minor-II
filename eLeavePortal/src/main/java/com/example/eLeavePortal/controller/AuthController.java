package com.example.eLeavePortal.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.eLeavePortal.dto.AuthUserDto;
import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // ================== Register ==================
    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody AuthUserDto userDto) {
        authService.register(userDto);
        return Map.of("message", "Registration successful.");
    }
    
    // ================== Login ==================
    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody AuthUserDto userDto, HttpSession session) {
        UserEntity userEntity = authService.login(userDto);
        session.setAttribute("USERNAME", userEntity.getUserName());
        session.setAttribute("USERROLE", userEntity.getRole());
        return Map.of("message", "Login successful.");
    }

    // ================== Logout ==================
    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session){
        session.invalidate();
        return Map.of("message", "Logged out.");
    }
}
