package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hkust_permissions")
public class Permission {

    @TableId("permission_id")
    private int permissionId;

    @TableField("permissionName")
    private String permission_name;

    @TableField("urlPattern")
    private String url_pattern;

    @TableField("httpMethod")
    private String http_method;
}
