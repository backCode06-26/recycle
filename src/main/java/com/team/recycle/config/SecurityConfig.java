package com.team.recycle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/members/login", "/members/join", "/members/userInfo").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((auth) -> auth
                        .loginPage("/members/login")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .csrf((csrf) -> csrf.disable());

        return http.build();
    }
}
