package com.hkust.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("hkust_role_perm")
@Data
public class RolePerm {

    @TableField("role_id")
    private int roleId;

    @TableField("perm_id")
    private int permId;

    @TableField("perm_url_id")
    private int permUrlId;

    @TableField("perm_url")
    private String permUrl;

    private String channel;

}
