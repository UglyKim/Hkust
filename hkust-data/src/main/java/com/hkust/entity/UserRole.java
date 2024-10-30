package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hkust_user_role")
public class UserRole {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("student_id")
    private String studentId;

    @TableField("role_id")
    private String roleId;
}
