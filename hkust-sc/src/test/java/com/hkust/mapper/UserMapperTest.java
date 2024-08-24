package com.hkust.mapper;

import cn.hutool.json.JSONUtil;
import com.hkust.HkustBaseTest;
import com.hkust.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserMapperTest extends HkustBaseTest {

    private UserMapper userMapper;

    @Test
    public void getAllUsers() {
        List<User> userList = userMapper.selectAll();
        log.info(JSONUtil.toJsonPrettyStr(userList));
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
