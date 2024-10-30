package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.hkust.dto.ApiResponse;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.AddUserAO;
import com.hkust.wmc.dto.ao.AlterUserAO;
import com.hkust.wmc.dto.ao.UserQueryAO;
import com.hkust.wmc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/wmc/v1/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Operation(summary = "新增学员")
    @PostMapping("/add")
    public ApiResponse addUSer(@RequestBody AddUserAO addUserAO) {
        log.info("received add user info:{}", JSONUtil.toJsonPrettyStr(addUserAO));
        return userService.addUSer(addUserAO);
    }

    @Operation(summary = "用户列表")
    @PostMapping("/list")
    public ApiResponse<PageResponse> getUserList(@RequestBody UserQueryAO userQueryAO) {
        log.info("received user query info:{}", JSONUtil.toJsonPrettyStr(userQueryAO));
        return userService.getUserList(userQueryAO);
    }

    @Operation(summary = "查看用户详情")
    public ApiResponse getUserInfo(@RequestParam String studentId) {
        log.info("received user_id:{}", studentId);
        return userService.getUserInfoDetail(studentId);
    }

    @Operation(summary = "用户修改")
    @PostMapping("/alter")
    public ApiResponse alterUser(@RequestBody AlterUserAO alterUserAO) {
        log.info("received alter user info:{}", JSONUtil.toJsonPrettyStr(alterUserAO));
        return userService.alterUser(alterUserAO);
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete")
    public ApiResponse deleteUser(@RequestParam String studentId) {
        log.info("received student id:{}", studentId);
        return userService.disableUser(studentId);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
