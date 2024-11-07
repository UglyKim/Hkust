package com.hkust.entity.wms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("wms_stocktaking_record")
public class WmsStocktakingRecord {

    @TableId
    private String id;

    private String name;

    private int count;

    @TableField("operator_id")
    private String operatorId;

    private String operator;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private String remark;
}
