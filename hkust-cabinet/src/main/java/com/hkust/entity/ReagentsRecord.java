package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("hkust_reagents_record")
public class ReagentsRecord {
    
    @TableId("record_id")
    private String record_id;

    @TableField("reagentsId")
    private String reagents_id;

    @TableField("doorId")
    private String door_id;

    private String type;

    @TableField("opt_user_id")
    private String optUserId;

    @TableField("optTime")
    private LocalDate opt_time;
}
