package com.hkust.controller.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.ao.LoginInfoAO;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证")
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class AuthJwtController {

    private UserMapper userMapper;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private CustomUserDetailsService userDetailsService;

    private PasswordEncoder BCryptPasswordEncoder;

    @Operation(summary = "登陆认证")
    @PostMapping("/auth/login")
    public ApiResponse userAuthentication(@RequestBody LoginInfoAO loginInfoAO) throws Exception {
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

        if (!BCryptPasswordEncoder.matches(loginInfoAO.getPassword(), userDetails.getUser().getPassword())) {
            return ApiResponse.failed(ReturnCode.PASSWD_MISMATCH);
        }

        if (!userDetails.isEnabled()) {
            return ApiResponse.failed(ReturnCode.USER_IS_DISABLE);
        }
        return ApiResponse.success(jwtTokenUtil.generateToken(userDetails));

        /*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userMapper.selectByStudentId(loginInfoAO.)
        User user = userMapper.selectByStudentIdAndPasswd(loginInfoAO.getStudentId(), BCryptPasswordEncoder.encode(loginInfoAO.getPassword()));
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        if (!user.getEnabled()) {
            return ApiResponse.failed(ReturnCode.USER_IS_DISABLE);
        }
        CustomUserDetails userDetails = new CustomUserDetails(user);
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ApiResponse.success(jwt);*/
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

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setBCryptPasswordEncoder(PasswordEncoder BCryptPasswordEncoder) {
        this.BCryptPasswordEncoder = BCryptPasswordEncoder;
    }
}
