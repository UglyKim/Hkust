package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
import com.hkust.wmc.dto.vo.UserVO;
import com.hkust.wmc.struct.structmapper.WmcUserStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;

    public ApiResponse getUserList() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("stat", "1");
        List<User> userList = userMapper.selectList(wrapper);

        List<UserVO> userVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(userList)) {
            for (User user : userList) {
                UserVO userVO = WmcUserStructMapper.INSTANCE.userToUserVO(user);
                userVOList.add(userVO);
            }
        }
        return ApiResponse.success(userVOList);
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
