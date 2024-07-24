package com.hkust.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("hkust_event")
public class Event {

    @TableField("event_id")
    private String eventId;

    @TableField("cabinet_id")
    private String cabinetId;

    @TableId("type")
    private String eventType;

    private String content;

    @TableField("opt_date")
    private LocalDate optDate;
}
