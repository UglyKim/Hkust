package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@TableName("hkust_permissions")
public class Permission {

    @TableId("permission_id")
    private int permissionId;

    @TableField("permission_name")
    private String permissionName;

    @TableField("describe")
    private String describe;

    private List<UrlPermissions> urlPermissionList;
}
