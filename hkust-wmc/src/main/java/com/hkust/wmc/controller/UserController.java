package com.hkust.wmc.controller;

import com.hkust.dto.ApiResponse;
import com.hkust.wmc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户")
@RestController
@RequestMapping("/wmc/v1/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Operation(summary = "用户列表")
    @PostMapping("/list")
    public ApiResponse getUserList() {
        return userService.getUserList();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
