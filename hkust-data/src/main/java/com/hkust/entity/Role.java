package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("hkust_role")
public class Role {

    @TableId("role_id")
    private int roleId;

    @TableField("role_name")
    private String roleName;

//    private List<Perm> permList;
}
