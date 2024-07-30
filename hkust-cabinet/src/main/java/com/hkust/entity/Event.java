package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("hkust_event")
public class Event {

    @TableField("event_id")
    private String eventId;

    @TableField("cabinet_id")
    private String cabinetId;

    @TableField("door_id")
    private String doorId;

    @TableField("opt_student_id")
    private String optStudentId;

    @TableId("type")
    private String eventType;

    private String content;

    @TableField("opt_date")
    private LocalDateTime optDate;
}
