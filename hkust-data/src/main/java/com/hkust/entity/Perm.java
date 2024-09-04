package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("hkust_perm")
public class Perm {

    @TableId("perm_id")
    private int permId;

    @TableField("perm_name")
    private String permName;

    @TableField("describe")
    private String describe;

    private String channel;

    private List<PermDetail> urlPermissionList;
}
