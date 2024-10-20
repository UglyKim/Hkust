package com.hkust.entity.wms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wms_opt_log")
public class WmsOptLog {

    @TableId("id")
    private String id;

    private String type;

    @TableField("operator_id")
    private String operatorId;

    private String operator;

    private LocalDateTime opt_time;
}
