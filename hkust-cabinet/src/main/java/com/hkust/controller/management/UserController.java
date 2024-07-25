package com.hkust.controller.management;

import cn.hutool.core.util.StrUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.UserAlterInfoAO;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import com.hkust.service.UserService;
import com.hkust.dto.ao.UserInfoAO;
import com.hkust.struct.structmapper.UserStructMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
        throw new NullPointerException();
    }

    @Operation(summary = "用户列表")
    @PostMapping("/list")
    public ApiResponse<PageResponse> updateUser() {
        return userService.getAllUser();
    }

    @Operation(summary = "新增用户")
    @PostMapping("/add")
    public ApiResponse addUser(@RequestBody UserInfoAO userInfoAO) {
        log.info("Received user_info:{}", StrUtil.toString(userInfoAO));
        return userService.addUser(userInfoAO);
    }

    @Operation(summary = "修改用户")
    @PostMapping("/update")
    public ApiResponse updateUser(@RequestBody UserAlterInfoAO userAlterInfoAO) {
        log.info("Received user_info:{}", StrUtil.toString(userAlterInfoAO));
        return userService.updateUser(userAlterInfoAO);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
