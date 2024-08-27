package com.hkust.security.jwt;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.hkust.security.HkustUserDetails;
import com.hkust.security.HkustUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private HkustUserDetailsService customUserDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    public JwtFilter(HkustUserDetailsService customUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String studentId = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                studentId = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
                throw new IllegalAccessError("无法获取 JWT 令牌");
            } catch (ExpiredJwtException e) {
                log.error("JWT Token has expired");
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }
        if (studentId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String requestURI = request.getRequestURI();
            log.info("uri:{}", requestURI);
            log.info("studentId:{}", studentId);
            HkustUserDetails userDetails = (HkustUserDetails) this.customUserDetailsService.loadUserByUsername(studentId);
            log.info("userDetails is:{}", JSONUtil.toJsonPrettyStr(userDetails));
            log.info("getUser:{}", userDetails.getUser());
            if (jwtTokenUtil.validateToken(jwtToken, requestURI, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
//        SecurityContextHolder.clearContext();
    }
}
