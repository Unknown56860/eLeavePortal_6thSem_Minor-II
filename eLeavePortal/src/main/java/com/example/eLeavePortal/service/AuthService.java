package com.example.eLeavePortal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.eLeavePortal.dto.AuthUserDto;
import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.repository.UserRepository;
import com.example.eLeavePortal.service.intrf.AuthServiceInterface;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(AuthUserDto userDto){
        if(userRepository.existsByUserName(userDto.getUserName())) {
            throw new RuntimeException("User already exist.");
        }

        if(userDto.getRole() == null){
            throw new RuntimeException("Role is required.");
        }

        UserEntity user = UserEntity.builder()
            .userName(userDto.getUserName())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .role(userDto.getRole())
            .build();
        
        userRepository.save(user);
    }

    @Override
    public UserEntity login(AuthUserDto userDto){
        UserEntity userEntity = userRepository.findByUserName(userDto.getUserName())
            .orElseThrow(() -> new RuntimeException("User doesn't exist."));
        
        if(!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Wrong password.");
        }

        return userEntity;
    }
}
