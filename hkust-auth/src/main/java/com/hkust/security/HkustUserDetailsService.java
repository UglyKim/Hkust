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

@Service
@Slf4j
public class HkustUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        User user = userMapper.selectUserRoleByStudentId(studentId);
        if (ObjectUtil.isEmpty(user)) {
            throw new UsernameNotFoundException("user is null!");
        }
        if (ObjectUtil.isEmpty(user.getRoleList())) {
            throw new UsernameNotFoundException("role is null!");
        }
//        Collection<GrantedAuthority> authorities = user.getRoles().stream()
//                .flatMap(role -> role.getPermissions().stream())
//                .map(Perm -> new SimpleGrantedAuthority(Perm.getPermissionName()))
//                .collect(Collectors.toList());

//        roles.stream().flatMap(role->role)

        if (user.getStudentId().equals(studentId)) {
            return new HkustUserDetails(user);
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
    public UserDetails loadUserByUsername_bak(String username) throws UsernameNotFoundException {

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
