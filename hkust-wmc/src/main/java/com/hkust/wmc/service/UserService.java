package com.hkust.wmc.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import com.hkust.entity.Role;
import com.hkust.entity.User;
import com.hkust.entity.UserExts;
import com.hkust.entity.UserRole;
import com.hkust.enums.EnableEnum;
import com.hkust.enums.StatEnum;
import com.hkust.mapper.RoleMapper;
import com.hkust.mapper.UserExtsMapper;
import com.hkust.mapper.UserMapper;
import com.hkust.mapper.UserRoleMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import com.hkust.wmc.dto.PageResponse;
import com.hkust.wmc.dto.ao.AddUserAO;
import com.hkust.wmc.dto.ao.AlterUserAO;
import com.hkust.wmc.dto.ao.UserQueryAO;
import com.hkust.wmc.dto.vo.UserVO;
import com.hkust.wmc.struct.structmapper.WmcUserStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserService {

    private UserMapper userMapper;

    private RoleMapper roleMapper;

    private PasswordEncoder BCryptPasswordEncoder;

    private UserRoleMapper userRoleMapper;

    private UserExtsMapper userExtsMapper;

    public ApiResponse getRoles() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        List<Role> roles = roleMapper.selectList(wrapper);
//        List<Role> roles = roleMapper.selectAll("sc");
        if (CollUtil.isEmpty(roles)) {
            return ApiResponse.failed(ReturnCode.ROLL_IS_NULL);
        }
        List<Map<String, String>> roleList = new ArrayList<>();
        for (Role role : roles) {
            Map<String, String> roleMap = new HashMap<>();
            Field[] fields = Role.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("roleId")) {
                    roleMap.put("roleId", String.valueOf(role.getRoleId()));
                }
                if (field.getName().equals("roleName")) {
                    roleMap.put("name", role.getRoleName());
                }
            }
            roleList.add(roleMap);
        }
        return ApiResponse.success(roleList);
    }

    public ApiResponse addUSer(AddUserAO addUserAO) {
        if (ObjectUtil.isEmpty(addUserAO.getStudentId())) {
            return ApiResponse.failed(ReturnCode.STUDENT_ID_NOT_NULL);
        }
        User selectedUser = userMapper.selectByStudentId(addUserAO.getStudentId());
        if (ObjectUtil.isNotEmpty(selectedUser)) {
            return ApiResponse.failed(ReturnCode.USER_ALREADY_EXISTS);
        }
        List<Role> roleList = SecurityUtils.getCurrentUser().getRoleList();
        boolean containsAdmin = roleList.stream()
                .anyMatch(role -> "admin".equals(role.getRoleName()));
        if (!containsAdmin) {
            return ApiResponse.failed(ReturnCode.NO_PERMISSION);
        }
        User user = WmcUserStructMapper.INSTANCE.userAOToUser(addUserAO);
        log.debug("User data to be inserted:{}", JSONUtil.toJsonPrettyStr(user));
        try {
            user.setUserId(UUIDUtils.generateUUIDWithoutHyphens());
            user.setStat(StatEnum.NORMAL.getCode());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strDateTime = now.format(formatter);
            LocalDateTime nowDateTime = LocalDateTime.parse(strDateTime, formatter);
            user.setCreateTime(nowDateTime);
            user.setPassword(BCryptPasswordEncoder.encode(addUserAO.getPassword()));
            userMapper.insert(user);
            // 添加版本
            UserExts userExts = new UserExts();
            userExts.setChannel("mc");
            userExts.setStudentId(user.getStudentId());
            userExts.setVersion(1);
            userExtsMapper.insert(userExts);
            UserExts userExts1 = new UserExts();
            userExts1.setVersion(1);
            userExts1.setChannel("sc");
            userExts1.setStudentId(user.getStudentId());
            userExtsMapper.insert(userExts1);
            // 添加用户角色关系
            UserRole userRole = new UserRole();
            userRole.setStudentId(user.getStudentId());
            userRole.setRoleId(addUserAO.getRoleId());
            userRoleMapper.insert(userRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }

    public ApiResponse<PageResponse> getUserList(UserQueryAO userQueryAO) {

        Map<String, Object> paramsMap = new HashMap<>();
        if (ObjectUtil.isNotEmpty(userQueryAO.getUserName())) {
            paramsMap.put("userName", userQueryAO.getUserName());
        }
        paramsMap.put("pageSize", userQueryAO.getPageSize());
        int offset = 0;
        if (0 != userQueryAO.getPageNum()) {
            offset = (userQueryAO.getPageNum() - 1) * userQueryAO.getPageSize();
        }
        paramsMap.put("offset", offset);
        List<User> userList = userMapper.selectUserRoleListByCondition(paramsMap);
//        Page<User> page = new Page(userQueryAO.getPageNum(), userQueryAO.getPageSize());
//        QueryWrapper<User> wrapper = new QueryWrapper<User>();
//        wrapper.eq("stat", "1");
//        if (ObjUtil.isNotEmpty(userQueryAO.getUserName())) {
//            wrapper.eq("user_name", userQueryAO.getUserName());
//        }
//        IPage<User> userIPage = userMapper.selectPage(page, wrapper);
//        List<User> userList = userIPage.getRecords();
        if (CollUtil.isEmpty(userList)) {
            return ApiResponse.success();
        }
        List<UserVO> userVOList = new ArrayList<>();
        if (CollUtil.isNotEmpty(userList)) {
            for (User user : userList) {
                List<String> roleNameList = user.getRoleList().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toList());
                UserVO userVO = WmcUserStructMapper.INSTANCE.userToUserVO(user);
                userVO.setStat(user.getStat().equals(EnableEnum.YES.getCode()) ? EnableEnum.YES.getName() : EnableEnum.NO.getName());
                userVO.setRoleList(roleNameList);
                userVOList.add(userVO);
            }
        }
        // 总数
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("stat", StatEnum.NORMAL.getCode());
        Long total = userMapper.selectCount(wrapper);
        PageResponse pageResponse = new PageResponse(userQueryAO.getPageNum(), userQueryAO.getPageSize(), total, userVOList);
        return ApiResponse.success(pageResponse);
    }

    public ApiResponse getUserInfoDetail(String studentId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);

        User user = userMapper.selectOne(wrapper);
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        UserVO userVO = WmcUserStructMapper.INSTANCE.userToUserVO(user);
        return ApiResponse.success(userVO);
    }

    public ApiResponse alterUser(AlterUserAO alterUserAO) {
        // 查询学生是否存在
        User user = userMapper.selectByStudentId(alterUserAO.getStudentId());
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        // 更新用户
        user.setUpdateTime(DateUtils.getCurrentDateTime());
        UpdateChainWrapper<User> chainWrapper = new UpdateChainWrapper<>(userMapper);
        chainWrapper.eq("student_id", alterUserAO.getStudentId());
        if (ObjectUtil.isNotEmpty(alterUserAO.getPassword())) {
            String password = BCryptPasswordEncoder.encode(alterUserAO.getPassword());
            chainWrapper.set("password", password);
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getAddress())) {
            chainWrapper.set("address", alterUserAO.getAddress());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getEmail())) {
            chainWrapper.set("email", alterUserAO.getEmail());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getUsername())) {
            chainWrapper.set("user_name", alterUserAO.getUsername());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getRealName())) {
            chainWrapper.set("real_name", alterUserAO.getRealName());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getGender())) {
            chainWrapper.set("gender", alterUserAO.getGender());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getOfficeLocation())) {
            chainWrapper.set("office_location", alterUserAO.getOfficeLocation());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getFixedTel())) {
            chainWrapper.set("fixed_tel", alterUserAO.getFixedTel());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getPhone())) {
            chainWrapper.set("phone", alterUserAO.getPhone());
        }
        if (ObjectUtil.isNotEmpty(alterUserAO.getEnabled())) {
            if (alterUserAO.getEnabled()) {
                chainWrapper.set("stat", EnableEnum.YES.getCode());
            } else {
                chainWrapper.set("stat", EnableEnum.NO.getCode());

            }
        }
        chainWrapper.update();
        return ApiResponse.success();
    }

    public ApiResponse disableUser(String studentId) {
        User user = userMapper.selectByStudentId(studentId);
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed(ReturnCode.USER_IS_NULL);
        }
        UpdateChainWrapper<User> chainWrapper = new UpdateChainWrapper<>(userMapper);
        chainWrapper.eq("student_id", studentId);
        chainWrapper.set("stat", EnableEnum.NO.getCode());
        chainWrapper.update();
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

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setUserExtsMapper(UserExtsMapper userExtsMapper) {
        this.userExtsMapper = userExtsMapper;
    }
}
