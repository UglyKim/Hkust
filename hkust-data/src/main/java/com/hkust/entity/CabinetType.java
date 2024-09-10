package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("hkust_cabinet_type")
public class CabinetType {

    private String code;

    private String name;
    // 型号
    private String model;
    // 规格
    private String specs;

    @TableField("safety_level")
    private String safetyLevel;

    // 序列号
    @TableField("serial_number")
    private String serialNumber;

    private String remark;
}
