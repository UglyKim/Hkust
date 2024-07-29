package com.hkust.mapper;

import com.hkust.HkustBaseTest;
import com.hkust.entity.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest extends HkustBaseTest {

    UserMapper userMapper;


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

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("000000"));
    }
}