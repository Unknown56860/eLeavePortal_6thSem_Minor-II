package com.example.eLeavePortal.controller;

import java.util.Map;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.eLeavePortal.dto.AuthUserDto;
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
    // Taken care by spring security.
    // @PostMapping("/login")
    // public Map<String, String> login(@Valid @RequestBody AuthUserDto userDto, HttpSession session) {
    //     UserEntity userEntity = authService.login(userDto);

    //     session.setAttribute("userId", userEntity.getUserId());
    //     session.setAttribute("userName", userEntity.getUserName());
    //     session.setAttribute("userRole", userEntity.getRole());

    //     return Map.of(
    //         "message", "Login successful."
    //     );
    // }

    @GetMapping("/session")
    public Map<String, Object> getSession(Authentication auth){

        // Not needed since Spring Security handles all.
        // HttpSession session.getUserName(), session.getUserPassword
        
        return Map.of(
            "userName", auth.getName(),
            "userRole", auth.getAuthorities()
        );
    }

    // ================== Logout ==================
    // Taken care by Spring Security
    // @PostMapping("/logout")
    // public Map<String, String> logout(HttpSession session) {

    //     session.invalidate();

    //     return Map.of(
    //         "message", "Logout successful."
    //     );
    // }

}
