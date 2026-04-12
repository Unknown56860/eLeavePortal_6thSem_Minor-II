package com.example.eLeavePortal.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.eLeavePortal.dto.AuthUserDto;
import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.enums.RolesEnum;
import com.example.eLeavePortal.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(AuthUserDto userDto){
        if(userRepository.existsByUserName(userDto.getUserName())) {
            throw new RuntimeException("User already exist.");
        }

        if(userDto.getRole() == null){
            throw new RuntimeException("Role is required.");
        }
        else if (userDto.getRole() == RolesEnum.ADMIN){
            throw new AccessDeniedException("Action not allowed!");
        }

        UserEntity user = UserEntity.builder()
            .userName(userDto.getUserName())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .role(userDto.getRole())
            .build();
        
        userRepository.save(user);
    }

    public void registerAdmin(AuthUserDto userDto){
        if(userRepository.existsByUserName(userDto.getUserName())) {
            throw new RuntimeException("User already exist");
        }

        if(userDto.getRole() != RolesEnum.ADMIN){
            throw new RuntimeException("Go to register page");
        }

        UserEntity user = UserEntity.builder()
            .userName(userDto.getUserName())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .role(userDto.getRole())
            .build();
        
        userRepository.save(user);
    }


    // Taken care by Spring Security.
    // @Override
    // public UserEntity login(AuthUserDto userDto){
    //     UserEntity userEntity = userRepository.findByUserName(userDto.getUserName())
    //         .orElseThrow(() -> new RuntimeException("User doesn't exist."));
        
    //     if(!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
    //         throw new RuntimeException("Wrong password.");
    //     }

    //     return userEntity;
    // }
}
