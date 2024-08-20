package com.hkust.security.config;

import com.hkust.security.CustomUserDetailsService;
import com.hkust.security.jwt.JwtAuthenticationEntryPoint;
import com.hkust.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Jsr250Voter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtFilter jwtFilter;

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtFilter jwtFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtFilter = jwtFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 测试使用，不做验证
       /* http
                .authorizeRequests().anyRequest().permitAll() // 允许所有请求
                .and().csrf().disable(); // 禁用 CSRF 保护

        return http.build();*/
        http.csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        {
                            try {
                                authorizeRequests
                                        .antMatchers("/v1/admin/**").hasRole("admin")
                                        .antMatchers(
                                                "/v3/api-docs/**",
                                                "/swagger-ui/**",
                                                "/api/v1/auth/login"
                                        ).permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                        .and()
                                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                        .and()
                                        .formLogin()
                                        .usernameParameter("studentId")
                                        .permitAll();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}