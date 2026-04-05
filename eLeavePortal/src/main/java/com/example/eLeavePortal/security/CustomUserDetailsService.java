package com.example.eLeavePortal.security;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

import com.example.eLeavePortal.entity.UserContactEntity;
import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.repository.UserContactRepository;
import com.example.eLeavePortal.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        
        UserEntity user = userRepository.findByUserName(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        UserContactEntity contact = userContactRepository.findByUser(user).orElse(null);

        String realName = contact != null ? contact.getName() : null;

        return new CustomUserDetails(user, realName);
    }
}
