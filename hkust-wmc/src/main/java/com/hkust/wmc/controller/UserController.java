package com.hkust.wmc.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hkust.dto.ApiResponse;
import com.hkust.enums.EnableEnum;
import com.hkust.enums.UserStatEnum;
import com.hkust.utils.EnumToJsonUtils;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.AddUserAO;
import com.hkust.wmc.dto.ao.AlterUserAO;
import com.hkust.wmc.dto.ao.UserQueryAO;
import com.hkust.wmc.dto.vo.UserVO;
import com.hkust.wmc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(name = "用户")
@RestController
@RequestMapping("/wmc/v1/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Operation(summary = "是否启用")
    @PostMapping("/enabled")
    public ApiResponse<List<ObjectNode>> getUserEnabledType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(EnableEnum.class));
    }

    @Operation(summary = "用户状态")
    @PostMapping("/stat")
    public ApiResponse<List<ObjectNode>> getUserStatType() {
        return ApiResponse.success(EnumToJsonUtils.convertEnumToJsonList(UserStatEnum.class));
    }

    @Operation(summary = "角色列表")
    @PostMapping("/roles")
    public ApiResponse<List<Map<String, String>>> getRoles() {
        return userService.getRoles();
    }

    @Operation(summary = "新增学员")
    @PostMapping("/add")
    public ApiResponse<Void> addUSer(@Valid @RequestBody AddUserAO addUserAO) {
        log.info("received add user info:{}", JSONUtil.toJsonPrettyStr(addUserAO));
        return userService.addUser(addUserAO);
    }

    @Operation(summary = "用户列表")
    @PostMapping("/list")
    public ApiResponse<PageResponse<UserVO>> getUserList(@Valid @RequestBody UserQueryAO userQueryAO) {
        log.info("received user query info:{}", JSONUtil.toJsonPrettyStr(userQueryAO));
        return userService.getUserList(userQueryAO);
    }

    @Operation(summary = "查看用户详情")
    @PostMapping("/detail")
    public ApiResponse<UserVO> getUserInfo(@RequestParam String studentId) {
        log.info("received user_id:{}", studentId);
        return userService.getUserInfoDetail(studentId);
    }

    @Operation(summary = "用户修改")
    @PostMapping("/alter")
    public ApiResponse<Void> alterUser(@RequestBody AlterUserAO alterUserAO) {
        log.info("received alter user info:{}", JSONUtil.toJsonPrettyStr(alterUserAO));
        return userService.alterUser(alterUserAO);
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete")
    public ApiResponse<Void> deleteUser(@RequestParam String studentId) {
        log.info("received student id:{}", studentId);
        return userService.disableUser(studentId);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
