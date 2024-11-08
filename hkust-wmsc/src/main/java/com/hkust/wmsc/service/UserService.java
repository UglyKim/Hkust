package com.hkust.wmsc.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.wmsc.dto.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService {

    private UserMapper userMapper;

    private PasswordEncoder BCryptPasswordEncoder;

    public ApiResponse<UserVO> getUserInfo() {
        User currentUser = SecurityUtils.getCurrentUser();
        if (ObjectUtil.isEmpty(currentUser)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        UserVO userVO = new UserVO();
        userVO.setUserName(Optional.ofNullable(currentUser).map(User::getUsername).orElse(null));
        userVO.setStudentId(Optional.ofNullable(currentUser).map(User::getStudentId).orElse(null));
        return ApiResponse.success(userVO);
    }

    public ApiResponse<Void> alterUserPassword(String password) {
        User currentUser = SecurityUtils.getCurrentUser();
        if (ObjectUtil.isEmpty(currentUser)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        currentUser = userMapper.selectByStudentId(Optional.ofNullable(currentUser).map(User::getStudentId).orElse(null));
        currentUser.setPassword(BCryptPasswordEncoder.encode(password));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", currentUser.getStudentId());
        userMapper.update(currentUser, wrapper);
        return ApiResponse.success();
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setBCryptPasswordEncoder(PasswordEncoder BCryptPasswordEncoder) {
        this.BCryptPasswordEncoder = BCryptPasswordEncoder;
    }

}
