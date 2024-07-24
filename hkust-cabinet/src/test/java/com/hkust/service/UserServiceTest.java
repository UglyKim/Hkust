package com.hkust.service;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hkust.HkustBaseTest;
import com.hkust.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UserServiceTest extends HkustBaseTest {

    private final static String USER_NAME = "root";

    @Autowired
    private UserService userService;

    @Test
    void getAllUsers() {
        List<User> users = userService.getAllUsers();
        log.info("size:{}", users.size());
        Assert.notNull(users, "Users list should not be null");
    }

    @Test
    void getUserByUserName() {
        User user = userService.getUserByUserName(USER_NAME);
        Assert.notNull(user, "User should not be null");
        assertEquals("root", USER_NAME);
        log.info(user.getUsername());
        log.info(user.getPassword());
    }
}