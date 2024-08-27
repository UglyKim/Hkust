package com.hkust.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("hkust_url_permissions")
@Data
public class UrlPermissions {

    @TableId("url_id")
    private int urlId;

    @TableField("url_pattern")
    private String urlPattern;

    @TableField("permission_id")
    private int permission_id;

    @TableField("http_method")
    private String httpMethod;

    @TableField("describe")
    private String describe;
}

