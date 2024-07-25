package com.hkust.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.UserAlterInfoAO;
import com.hkust.dto.ao.UserInfoAO;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
import com.hkust.struct.structmapper.UserStructMapper;
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

    public ApiResponse addUser(UserInfoAO userInfoAO) {

        User user = UserStructMapper.INSTANCE.UserAOToUser(userInfoAO);
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }

    public ApiResponse updateUser(UserAlterInfoAO userAlterInfoAO) {


        return null;
    }

    public ApiResponse<PageResponse> getAllUser() {

        return null;
    }
}
