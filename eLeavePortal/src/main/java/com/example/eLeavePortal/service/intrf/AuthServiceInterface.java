package com.example.eLeavePortal.service.intrf;

import com.example.eLeavePortal.dto.AuthUserDto;
import com.example.eLeavePortal.entity.UserEntity;

public interface AuthServiceInterface {
    
    void register(AuthUserDto dto);

    UserEntity login(AuthUserDto dto);
}
