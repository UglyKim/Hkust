package com.hkust.mapper;

import com.hkust.HkustBaseTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest extends HkustBaseTest {

    UserMapper userMapper;


    @Ignore
    @Test
    public void insertUser() {

    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}