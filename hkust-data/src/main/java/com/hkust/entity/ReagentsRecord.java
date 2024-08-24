package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("hkust_reagents_record")
public class ReagentsRecord {

    @TableId("record_id")
    private String recordId;

    @TableField("barcode")
    private String barCode;

    @TableField("cabinet_id")
    private String cabinetId;

    @TableField("door_id")
    private String doorId;

    private String type;

    @TableField("opt_student_id")
    private String optStudentId;

    @TableField("opt_time")
    private LocalDateTime optTime;
}
