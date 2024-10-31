package com.hkust.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.ChainUpdate;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.hkust.dto.ApiResponse;
import com.hkust.constant.ReturnCode;
import com.hkust.dto.AuthResponseVO;
import com.hkust.dto.LoginInfoAO;
import com.hkust.entity.Event;
import com.hkust.entity.Role;
import com.hkust.entity.User;
import com.hkust.entity.UserExts;
import com.hkust.entity.wms.WmsOptLog;
import com.hkust.enums.EventTypeEnum;
import com.hkust.enums.OptTypeEnum;
import com.hkust.mapper.EventMapper;
import com.hkust.mapper.UserExtsMapper;
import com.hkust.mapper.UserMapper;
import com.hkust.mapper.wmsc.WmsOptLogMapper;
import com.hkust.security.SecurityUtils;
import com.hkust.utils.DateUtils;
import com.hkust.utils.UUIDUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hkust.security.HkustUserDetails;
import com.hkust.security.HkustUserDetailsService;
import com.hkust.security.jwt.JwtTokenUtil;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "认证")
@RestController
@RequestMapping("/v1/")
@Slf4j
public class AuthController {

    private UserMapper userMapper;

    private UserExtsMapper userExtsMapper;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private HkustUserDetailsService userDetailsService;

    private PasswordEncoder BCryptPasswordEncoder;

    private EventMapper eventMapper;

    private WmsOptLogMapper wmsOptLogMapper;

    @Operation(summary = "登出")
    @PostMapping("/auth/logout")
    public ApiResponse userLogOut() {
        User user = SecurityUtils.getCurrentUser();
        if (ObjectUtil.isEmpty(user)) {
            return ApiResponse.failed("用户未登陆");
        }
        QueryWrapper<UserExts> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", user.getStudentId());
        wrapper.eq("channel", System.getProperty("channel"));
        UserExts userExts = userExtsMapper.selectOne(wrapper);
        if (ObjectUtil.isEmpty(userExts)) {
            return ApiResponse.failed("Invalid token");
        }
        // 更新token版本
        UpdateChainWrapper<UserExts> chainWrapper = new UpdateChainWrapper(userExtsMapper);
        chainWrapper.eq("student_id", userExts.getStudentId());
        chainWrapper.set("version", userExts.getVersion() + 1);
        chainWrapper.update();


        // 添加操作日志
        WmsOptLog wmsOptLog = new WmsOptLog();
        wmsOptLog.setId(UUIDUtils.generateUUIDWithoutHyphens());
//        User user = SecurityUtils.getCurrentUser();
        wmsOptLog.setOperatorId(user.getUserId());
        wmsOptLog.setOperator(user.getUsername());
        wmsOptLog.setType(OptTypeEnum.LOGOUT.getCode());
        wmsOptLog.setOptTime(DateUtils.getCurrentDateTime());
        wmsOptLogMapper.insert(wmsOptLog);

        return ApiResponse.success();
    }

    @Operation(summary = "登陆认证")
    @PostMapping("/auth/login")
    public ApiResponse userAuthentication(@RequestBody LoginInfoAO loginInfoAO) throws Exception {
        log.info("Received authentication login_info: {}", JSONUtil.toJsonPrettyStr(loginInfoAO));
        HkustUserDetails userDetails = (HkustUserDetails) userDetailsService.loadUserByUsername(loginInfoAO.getStudentId());

        if (!BCryptPasswordEncoder.matches(loginInfoAO.getPassword(), userDetails.getUser().getPassword())) {
            return ApiResponse.failed(ReturnCode.PASSWD_MISMATCH);
        }

        if (!userDetails.isEnabled()) {
            return ApiResponse.failed(ReturnCode.USER_IS_DISABLE);
        }
        // 更新用户token版本号
        updateVersion(userDetails, loginInfoAO.getChannel());
        // 添加登陆日志
        this.addEvent(loginInfoAO.getChannel(), userDetails.getUser());

        // 添加操作日志
        // 添加操作日志
        WmsOptLog wmsOptLog = new WmsOptLog();
        wmsOptLog.setId(UUIDUtils.generateUUIDWithoutHyphens());
        User user = userMapper.selectByStudentId(loginInfoAO.getStudentId());
        wmsOptLog.setOperatorId(user.getUserId());
        wmsOptLog.setOperator(user.getUsername());
        wmsOptLog.setType(OptTypeEnum.LOGIN.getCode());
        wmsOptLog.setOptTime(DateUtils.getCurrentDateTime());
        wmsOptLogMapper.insert(wmsOptLog);

        String token = jwtTokenUtil.generateToken(userDetails, loginInfoAO.getChannel());
        List<String> roles = userDetails.getUser().getRoleList().stream().map(Role::getRoleName).collect(Collectors.toList());
        AuthResponseVO authResponseVO = new AuthResponseVO(token, roles);
        return ApiResponse.success(authResponseVO);
    }

    private void updateVersion(HkustUserDetails userDetails, String channel) {
        User user = userDetails.getUser();

        QueryWrapper<UserExts> wrapper = new QueryWrapper();
        wrapper.eq("student_id", userDetails.getUser().getStudentId());
        wrapper.eq("channel", channel);
        UserExts userExts = userExtsMapper.selectOne(wrapper);
        userExts.setVersion(userExts.getVersion() + 1);

        UpdateWrapper<UserExts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("student_id", userDetails.getUser().getStudentId());
        updateWrapper.eq("channel", channel);
        userExtsMapper.update(userExts, updateWrapper);
    }


    private void addEvent(String channel, User user) {
        Event event = new Event();
        event.setEventId(UUIDUtils.generateUUIDWithoutHyphens());
        event.setOptStudentId(user.getStudentId());
        event.setEventType(EventTypeEnum.LOGIN.getCode());
        event.setContent(EventTypeEnum.LOGIN.getName());
        event.setChannel(channel);
        event.setOptDate(DateUtils.getCurrentDateTime());
        eventMapper.insert(event);
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setUserDetailsService(HkustUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
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
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Autowired
    public void setUserExtsMapper(UserExtsMapper userExtsMapper) {
        this.userExtsMapper = userExtsMapper;
    }

    @Autowired
    public void setWmsOptLogMapper(WmsOptLogMapper wmsOptLogMapper) {
        this.wmsOptLogMapper = wmsOptLogMapper;
    }
}
