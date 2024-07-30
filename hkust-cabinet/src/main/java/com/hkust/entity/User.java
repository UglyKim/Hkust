package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hkust.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("hkust_user")
public class User {

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

    private Boolean enabled;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
