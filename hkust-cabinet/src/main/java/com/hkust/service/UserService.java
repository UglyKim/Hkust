package com.hkust.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.dto.PageResponse;
import com.hkust.dto.ao.UserAlterInfoAO;
import com.hkust.dto.ao.UserInfoAO;
import com.hkust.dto.vo.UserVO;
import com.hkust.entity.User;
import com.hkust.enums.EnableEnum;
import com.hkust.enums.GenderEnum;
import com.hkust.mapper.UserMapper;
import com.hkust.security.CustomUserDetails;
import com.hkust.struct.structmapper.UserStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;

    private PasswordEncoder BCryptPasswordEncoder;

    public List<User> getAllUsers() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    public ApiResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        UserVO userVO = UserStructMapper.INSTANCE.UserToUserVO(user);
        userVO.setEnabled(user.getEnabled() ? EnableEnum.YES.getCode() : EnableEnum.NO.getCode());
        return ApiResponse.success(userVO);
    }

    public ApiResponse addUser(UserInfoAO userInfoAO) {

        User user = UserStructMapper.INSTANCE.UserAOToUser(userInfoAO);
        try {
            user.setEnabled(true);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strDateTime = now.format(formatter);
            LocalDateTime nowDateTime = LocalDateTime.parse(strDateTime, formatter);
            user.setCreateTime(nowDateTime);
            user.setPassword(BCryptPasswordEncoder.encode(userInfoAO.getPassword()));
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }

    public ApiResponse updateUser(UserAlterInfoAO userAlterInfoAO) {

        // 查询学生是否存在
        User user = userMapper.selectByUserName(userAlterInfoAO.getUsername());
        if (ObjectUtil.isEmpty(user)) {
            throw new NullPointerException("学生不存在!");
        }
        UpdateChainWrapper<User> chainWrapper = new UpdateChainWrapper<>(userMapper);
        chainWrapper.eq("user_name", userAlterInfoAO.getUsername());

        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getAddress())) {
            chainWrapper.set("address", userAlterInfoAO.getAddress());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getEmail())) {
            chainWrapper.set("email", userAlterInfoAO.getEmail());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getUsername())) {
            chainWrapper.set("user_name", userAlterInfoAO.getUsername());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getGender())) {
            chainWrapper.set("gender", userAlterInfoAO.getGender());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getOfficeLocation())) {
            chainWrapper.set("office_location", userAlterInfoAO.getOfficeLocation());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getFixedTel())) {
            chainWrapper.set("fixed_tel", userAlterInfoAO.getFixedTel());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getPhone())) {
            chainWrapper.set("phone", userAlterInfoAO.getPhone());
        }
        if (ObjectUtil.isNotEmpty(userAlterInfoAO.getPassword())) {
            chainWrapper.set("password", BCryptPasswordEncoder.encode(userAlterInfoAO.getPassword()));
        }
        chainWrapper.update();
        return ApiResponse.success();
    }

    public ApiResponse<PageResponse> getAllUser(String pageNum, String pageSize) {
        long num = Long.valueOf(pageNum);
        if (StrUtil.isEmpty(pageNum)) {
            num = 1;
        }
        long size = Long.valueOf(pageSize);
        Page<User> page = new Page<User>(num, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        List<User> userList = userIPage.getRecords();
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO userVO = UserStructMapper.INSTANCE.UserToUserVO(user);
            userVO.setEnabled(user.getEnabled() ? EnableEnum.YES.getName() : EnableEnum.NO.getName());
            if (user.getGender().equals(GenderEnum.F.name())) {
                userVO.setGender(GenderEnum.F.getName());
            } else {
                userVO.setGender(GenderEnum.M.getName());
            }
            userVOList.add(userVO);
        }
        PageResponse pageResponse = new PageResponse<UserVO>((int) num, (int) size, userIPage.getTotal(), userVOList);
        return ApiResponse.success(pageResponse);
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
