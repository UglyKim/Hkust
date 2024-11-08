package com.hkust.wmsc.controller;

import com.hkust.dto.ApiResponse;
import com.hkust.wmsc.dto.vo.UserVO;
import com.hkust.wmsc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户")
@RestController
@RequestMapping("/wmsc/v1/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Operation(summary = "用户详情")
    @PostMapping("/detail")
    private ApiResponse<UserVO> getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(summary = "修改密码")
    @PostMapping("/alter/passwd")
    private ApiResponse<Void> alterUserPassword(@RequestParam String passwd) {
        log.info("received passwd:{}", passwd);
        return userService.alterUserPassword(passwd);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
