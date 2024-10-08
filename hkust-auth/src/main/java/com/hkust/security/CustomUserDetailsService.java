package com.hkust.security;

import cn.hutool.core.util.ObjectUtil;
import com.hkust.entity.User;
import com.hkust.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        User user = userMapper.selectByStudentId(studentId);
        List<String> roles = userMapper.selectRolesByStudentId(studentId);
        if (ObjectUtil.isEmpty(user) || ObjectUtil.isEmpty(roles)) {
            throw new UsernameNotFoundException("user is null!");
        }
        if (user.getStudentId().equals(studentId)) {
            return new CustomUserDetails(user, roles);
        } else {
            throw new UsernameNotFoundException("User not found with student_id: " + studentId);
        }
    }

    /**
     * 使用username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Deprecated
//    @Override
    public UserDetails loadUserByUsername_123(String username) throws UsernameNotFoundException {

//        com.hkust.entity.User user = userMapper.selectByUserName(username);
//        log.info("selected user_name:{}", user.getUsername());
//        if (user.getUsername().equals(username)) {
//            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
        return null;
    }

    /**
     * @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     * <p>
     * com.hkust.entity.User user = userMapper.selectByUserName(username);
     * log.info("selected user_name:{}", user.getUsername());
     * if (user.getUsername().equals(username)) {
     * return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
     * } else {
     * throw new UsernameNotFoundException("User not found with username: " + username);
     * }
     * }
     */

    // 不接数据库的代码块
       /* PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("password");
        log.info("start......");
        if ("user".equals(username)) {
            return new User("user", password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }*/
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
