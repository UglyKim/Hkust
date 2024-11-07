package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@TableName("hkust_user")
public class User {

    @TableField("user_id")
    private String userId;

    @TableId("student_id")
    private String studentId;

    @TableField("user_name")
    private String username;

    @TableField("real_name")
    private String realName;

    @TableField("dept")
    private String dept;

    private String position;

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

    private String gender;

    private Boolean enabled;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("modified_time")
    private LocalDateTime modifiedTime;

    @TableField("add_ch")
    private String addCh;

    private List<Role> roleList;
}
