package com.hkust.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    public User getUserByUserName(String userName) {
        User user = userMapper.selectByUserName(userName);
        return user;
    }
}
