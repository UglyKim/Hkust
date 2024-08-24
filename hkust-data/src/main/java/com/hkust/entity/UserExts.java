package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hkust_user_exts")
public class UserExts {

    @TableField("student_id")
    private String studentId;

    private String channel;

    private int version;

}
