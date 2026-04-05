package com.example.eLeavePortal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
    
    private final UserEntity user;
    private final String realName;

    public CustomUserDetails(UserEntity user, String realName) {

        this.user = user;
        this.realName = realName;
    }

    public RolesEnum getRole() { return user.getRole(); }
    public ProfileStatusEnums getProfileStatus() { return user.getProfileStatus(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getUserName(); }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
