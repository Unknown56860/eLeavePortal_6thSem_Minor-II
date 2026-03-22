package com.example.eLeavePortal.security;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        
        UserEntity user = userRepository.findByUserName(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
