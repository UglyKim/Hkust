package com.hkust.controller.auth;

import cn.hutool.json.JSONUtil;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.LoginInfoAO;
import com.hkust.security.CustomUserDetails;
import com.hkust.security.CustomUserDetailsService;
import com.hkust.security.dto.JwtRequest;
import com.hkust.security.dto.JwtResponse;
import com.hkust.security.jwt.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证")
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class AuthJwtController {

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private CustomUserDetailsService userDetailsService;

    @Operation(summary = "登陆认证")
    @PostMapping("/auth/login")
    public ApiResponse createAuthenticationToken(@RequestBody LoginInfoAO loginInfoAO) throws Exception {
        log.info("Received authentication login_info: {}", JSONUtil.toJsonPrettyStr(loginInfoAO));

       /* try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginInfoAO.getStudentId(), loginInfoAO.getPassword())
            );
        } catch (Exception e) {
            log.error("Authentication failed for username: {}", loginInfoAO.getStudentId(), e);
            throw new Exception("Invalid username or password", e);
        }*/
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginInfoAO.getStudentId());
        if (!userDetails.isEnabled()) {
            return ApiResponse.failed(ReturnCode.USER_IS_DISABLE);
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ApiResponse.success(jwt);
    }

    /**
     * 备份 createAuthenticationToken1的  1 去掉
     *
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @Deprecated
    @Operation(summary = "认证")
    @PostMapping("/api/v1/auth/authenticate1")
//    public ApiResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    public ResponseEntity<?> createAuthenticationToken1(@RequestBody JwtRequest authenticationRequest) throws Exception {
        log.info("Received authentication request for username: {}", authenticationRequest.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            log.error("Authentication failed for username: {}", authenticationRequest.getUsername(), e);
            throw new Exception("Invalid username or password", e);
        }
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
