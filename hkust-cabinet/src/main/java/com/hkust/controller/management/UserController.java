package com.hkust.controller.management;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.UserAlterInfoAO;
import com.hkust.dto.ao.query.UserQueryAO;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import com.hkust.enums.EnableEnum;
import com.hkust.enums.GenderEnum;
import com.hkust.service.UserService;
import com.hkust.dto.ao.UserInfoAO;
import com.hkust.struct.structmapper.UserStructMapper;
import com.hkust.utils.EnumToJsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "用户")
@RestController
@RequestMapping("/v1")
@Slf4j
public class UserController {

    private UserService userService;

    @Operation(summary = "获取用户信息")
    @PostMapping("/info")
    public ApiResponse getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(summary = "用户列表")
    @PostMapping("/admin/list")
    public ApiResponse<PageResponse> getAllUser(@RequestBody UserQueryAO userQueryAO) {
        log.info("received user query info:{}", JSONUtil.toJsonPrettyStr(userQueryAO));
        return userService.getAllUser(userQueryAO);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/admin/add")
    public ApiResponse addUser(@RequestBody UserInfoAO userInfoAO) {
        log.info("Received user_info:{}", JSONUtil.toJsonPrettyStr(userInfoAO));
        return userService.addUser(userInfoAO);
    }

    @Operation(summary = "修改用户")
    @PostMapping("/update")
    public ApiResponse updateUser(@RequestBody UserAlterInfoAO userAlterInfoAO) {
        log.info("Received user_info:{}", StrUtil.toString(userAlterInfoAO));
        return userService.updateUser(userAlterInfoAO);
    }

    @Operation(summary = "用户是否启用")
    @PostMapping("/enabled")
    public ApiResponse getUserEnabled() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(EnableEnum.class));
    }

    @Operation(summary = "性别")
    @PostMapping("/gender")
    public ApiResponse getGender() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonArray(GenderEnum.class));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
