package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("hkust_cabinet")
public class Cabinet {

    @TableId("cabinet_id")
    private String cabinetId;

    private String name;

    private String location;

    private String mac;

    @TableField("cabinet_addr")
    private String cabinetAddr;

    private Boolean stat;

    @TableField("open_mode")
    private String openMode;

    @TableField("create_time")
    private LocalDateTime createTime;

    private String remark;

}
