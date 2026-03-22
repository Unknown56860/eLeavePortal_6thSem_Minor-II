package com.example.eLeavePortal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.eLeavePortal.security.CustomAccessDeniedHandler;
import com.example.eLeavePortal.security.CustomAuthEntryPoint;
import com.example.eLeavePortal.security.CustomLoginSuccessHandler;
import com.example.eLeavePortal.security.CustomUserDetailsService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomLoginSuccessHandler successHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )

            .userDetailsService(userDetailsService)

            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authEntryPoint)
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/", "/contact", "/error").permitAll()
                .requestMatchers("/login", "/register", "/api/auth/register").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/staff/**").hasAnyAuthority("CT", "HOD", "WARDEN", "HOSTEL_CONTROLLER", "ADMIN")
                .requestMatchers("/student/**").hasAnyAuthority("STUDENT", "ADMIN")
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/api/auth/login")
                .usernameParameter("userName")
                .passwordParameter("password")
                .successHandler(successHandler)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/")
            );

        return http.build();
    }
}
