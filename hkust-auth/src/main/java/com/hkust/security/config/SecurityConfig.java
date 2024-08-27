package com.hkust.security.config;

import com.hkust.entity.User;
import com.hkust.exception.CustomAccessDeniedHandler;
import com.hkust.security.HkustAccessDecisionManager;
import com.hkust.security.HkustSecurityMetadataSource;
import com.hkust.security.interceptor.HkustFilterSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.hkust.security.HkustUserDetailsService;
import com.hkust.security.jwt.JwtAuthenticationEntryPoint;
import com.hkust.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtFilter jwtFilter;

    private HkustUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtFilter jwtFilter, HkustUserDetailsService customUserDetailsService) {
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
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(hkustFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v1/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .usernameParameter("studentId")
                .permitAll();
        return http.build();
//                .authorizeRequests(authorizeRequests ->
//                        {
//                            try {
//                                authorizeRequests
//                                        .antMatchers(
//                                                "/v3/api-docs/**",
//                                                "/swagger-ui/**",
//                                                "/swagger-ui.html",
//                                                "/swagger-resources/**",
//                                                "/webjars/**",
//                                                "/v1/auth/login"
//                                        ).permitAll()
//                                        .anyRequest().authenticated()
//                                        .and()
//                                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                                        .accessDeniedHandler(customAccessDeniedHandler())
//                                        .and()
//                                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                                        .and()
//                                        .formLogin()
//                                        .usernameParameter("studentId")
//                                        .permitAll();
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                );
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(hkustFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
//        return http.build();
        // 测试使用，不做验证
       /* http
                .authorizeRequests().anyRequest().permitAll() // 允许所有请求
                .and().csrf().disable(); // 禁用 CSRF 保护

        return http.build();*/
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new HkustAccessDecisionManager();
    }

    @Bean
    public HkustFilterSecurityInterceptor hkustFilterSecurityInterceptor() {
        return new HkustFilterSecurityInterceptor(hkustSecurityMetadataSource(), hkustAccessDecisionManager());
//        FilterSecurityInterceptor filter = new FilterSecurityInterceptor();
//        filter.setSecurityMetadataSource(hkustSecurityMetadataSource());
//        filter.setAccessDecisionManager(hkustAccessDecisionManager());
//        return (HkustFilterSecurityInterceptor) filter;
    }

    @Bean
    public HkustSecurityMetadataSource hkustSecurityMetadataSource() {
        return new HkustSecurityMetadataSource();
    }

    @Bean
    public HkustAccessDecisionManager hkustAccessDecisionManager() {
        return new HkustAccessDecisionManager();
    }

}