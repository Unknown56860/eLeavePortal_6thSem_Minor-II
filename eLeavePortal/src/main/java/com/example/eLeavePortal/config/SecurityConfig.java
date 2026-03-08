package com.example.eLeavePortal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/**"
                    // "/contact",
                    // "/login",
                    // "/register",
                    // "/css/**",
                    // "/js/**",
                    // "/images/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/staff/**").hasAnyAuthority("CT", "HOD", "WARDEN", "HOSTEL_CONTROLLER", "ADMIN")
                .requestMatchers("/student/**").hasAnyAuthority("STUDENT", "ADMIN")
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
