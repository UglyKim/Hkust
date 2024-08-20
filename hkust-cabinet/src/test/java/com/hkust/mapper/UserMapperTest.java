package com.hkust.mapper;

import cn.hutool.json.JSONUtil;
import com.hkust.HkustBaseTest;
import com.hkust.entity.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UserMapperTest extends HkustBaseTest {

    UserMapper userMapper;


    @Test
    public void selectRolesByStudentId() {
        List<String> roles = userMapper.selectRolesByStudentId("ST001");
        log.info(JSONUtil.toJsonPrettyStr(roles));
    }

    @Test
    public void findUser() {
        User user = userMapper.selectByStudentIdAndPasswd("ST001", "$2a$10$18BL6Vq4WccLYe2.Z8KSXOks/uI80nT1Dj83gTdeR1EZt04hAo.SW");
    }


    @Ignore
    @Test
    public void insertUser() {

    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

}