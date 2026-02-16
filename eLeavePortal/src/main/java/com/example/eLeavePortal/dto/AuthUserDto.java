package com.example.eLeavePortal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.example.eLeavePortal.enums.RolesEnum;

@Data
public class AuthUserDto {
    
    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    // Required only during registration
    private RolesEnum role;
}
