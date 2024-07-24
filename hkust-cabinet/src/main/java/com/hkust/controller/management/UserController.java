package com.hkust.controller.management;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
import com.hkust.service.UserService;
import com.hkust.dto.ao.UserInfo;
import com.hkust.structmapper.UserStructMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户")
@RestController
@RequestMapping("/api/v1/manage/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public ApiResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                UserVO userVO = UserStructMapper.INSTANCE.toVO(user);
                return ApiResponse.success(userVO);
            } else {
                User user = userService.getUserByUserName(((org.springframework.security.core.userdetails.User) principal).getUsername());
                UserVO userVO = UserStructMapper.INSTANCE.toVO(user);
                return ApiResponse.success(userVO);
            }
        }
        return null;
    }

    @Operation(summary = "新增用户")
    @PostMapping("/add")
    public ApiResponse addUser(@RequestBody UserInfo userInfo) {

        return null;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
