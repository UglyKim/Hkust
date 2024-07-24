package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hkust.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalTime;
import java.util.Collection;

@Data
@TableName("hkust_user")
@AllArgsConstructor
public class User implements UserDetails {

    @TableId("user_id")
    private String userId;

    @TableField("student_id")
    private String studentId;

    @TableField("user_name")
    private String username;

    @TableField("real_name")
    private String realName;

    @TableField("address")
    private String address;

    @TableField("office_location")
    private String officeLocation;

    private String phone;

    @TableField("fixed_tel")
    private String fixedTel;

    @TableField("stat")
    private String stat;

    private String password;

    private String email;

    private GenderEnum gender;

    @TableField("is_enable")
    private String enabled;

    @TableField("create_time")
    private LocalTime createTime;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
