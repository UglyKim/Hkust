package com.hkust.security;

import com.hkust.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {

        com.hkust.entity.User user = userMapper.selectByStudentId(studentId);
        log.info("selected user_name:{}", user.getUsername());
        if (user.getStudentId().equals(studentId)) {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
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

        com.hkust.entity.User user = userMapper.selectByUserName(username);
        log.info("selected user_name:{}", user.getUsername());
        if (user.getUsername().equals(username)) {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    /**
     * 使用username
     * @param username
     * @return
     * @throws UsernameNotFoundException
     *//*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.hkust.entity.User user = userMapper.selectByUserName(username);
        log.info("selected user_name:{}", user.getUsername());
        if (user.getUsername().equals(username)) {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }*/

    // 不接数据库的代码块
       /* PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("password");
        log.info("start......");
        if ("user".equals(username)) {
            return new User("user", password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }*/
}
